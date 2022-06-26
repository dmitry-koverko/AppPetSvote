package com.petsvote.dialog

import android.content.ContentResolver
import android.content.ContentUris
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.petsvote.core.BaseViewModel
import com.petsvote.dialog.enity.LocalPhoto
import com.petsvote.domain.entity.user.UserPet
import com.petsvote.domain.usecases.configuration.ISetImageUseCase
import com.petsvote.domain.usecases.configuration.impl.SetImageUseCase
import com.petsvote.domain.usecases.pet.ISetImagePetProfileUseCase
import com.petsvote.domain.usecases.user.IGetUserPetsUseCase
import com.petsvote.domain.usecases.user.IGetUserUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class SelectPhotoViewModel @Inject constructor(
    private val setImageUseCase: ISetImageUseCase,
    private val setImagePetUseCase: ISetImagePetProfileUseCase
) : BaseViewModel() {

    var localPhotos = MutableStateFlow<List<LocalPhoto>>(emptyList())
    var dismiss = MutableStateFlow(false)

    suspend fun getLocalImages(cursor: Cursor?, contentResolver: ContentResolver) {
        var listPhoto = mutableListOf<LocalPhoto>()
        coroutineScope {
            cursor?.use {
                    cursor ->
                while (cursor.moveToNext()) {
                    val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
                    val id = cursor.getLong(idColumn)
                    val contentUri: Uri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                    )
                    listPhoto.add(LocalPhoto(contentUri, null))
                }
            }
            localPhotos.value = listPhoto

        }
    }

    suspend fun setImage(bytes: ByteArray){
        var set = viewModelScope.async (Dispatchers.IO + Job()) { setImageUseCase.setImage(bytes) }
        set.await()
    }

    suspend fun setImagePet(bytes: ByteArray){
        var set = viewModelScope.async (Dispatchers.IO + Job()) { setImagePetUseCase.setImagePet(bytes) }
        set.await()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val setImageUseCase: ISetImageUseCase,
        private val setImagePetUseCase: ISetImagePetProfileUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == SelectPhotoViewModel::class.java)
            return SelectPhotoViewModel(
                setImageUseCase = setImageUseCase,
                setImagePetUseCase = setImagePetUseCase
            ) as T
        }

    }

}
