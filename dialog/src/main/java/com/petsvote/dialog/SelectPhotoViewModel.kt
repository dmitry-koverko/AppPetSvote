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
import com.petsvote.domain.usecases.user.IGetUserPetsUseCase
import com.petsvote.domain.usecases.user.IGetUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectPhotoViewModel @Inject constructor(
    private val setImageUseCase: ISetImageUseCase
) : BaseViewModel() {

    var localPhotos = MutableStateFlow<List<LocalPhoto>>(emptyList())

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

    fun setImage(bytes: ByteArray){
        viewModelScope.launch (Dispatchers.IO){
            setImageUseCase.setImage(bytes)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val setImageUseCase: ISetImageUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == SelectPhotoViewModel::class.java)
            return SelectPhotoViewModel(
                setImageUseCase = setImageUseCase
            ) as T
        }

    }

}
