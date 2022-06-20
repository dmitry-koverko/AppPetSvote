package com.petsvote.user

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.petsvote.core.BaseViewModel
import com.petsvote.domain.entity.user.UserPet
import com.petsvote.domain.usecases.configuration.*
import com.petsvote.domain.usecases.configuration.impl.GetImagesUseCase
import com.petsvote.domain.usecases.user.IGetUserPetsUseCase
import com.petsvote.domain.usecases.user.IGetUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import javax.inject.Inject

class UserProfileViewModel @Inject constructor(
    private val getAddPhotosSettingsUseCase: IGetAddPhotosSettingsUseCase,
    private val setAddPhotosSettingsUseCase: ISetAddPhotosSettingsUseCase,
    private val getUserUseCase: IGetUserUseCase,
    private val getImagesUseCase: IGetImagesUseCase,
    private val setImageCropUseCase: ISetImageCropUseCase
) : BaseViewModel() {

    var isAddPhoto = MutableStateFlow<Boolean>(true)
    var image = MutableStateFlow<ByteArray>(byteArrayOf())
    var imageCrop = MutableStateFlow<Bitmap?>(null)

    fun getConfiguration() {

        viewModelScope.launch(Dispatchers.IO) {
            isAddPhoto.emit(getAddPhotosSettingsUseCase.getAddPhotosSettings())
        }

        viewModelScope.launch (Dispatchers.IO) {
            getImagesUseCase.getImage().collect {
                if(it.imageCrop?.isNotEmpty() == true){
                    val imageStream = ByteArrayInputStream(it.imageCrop)
                    val theImage = BitmapFactory.decodeStream(imageStream)
                    imageCrop.emit(theImage)
                }
            }

        }
    }

    fun setAddPhotoDialog(){
        viewModelScope.launch (Dispatchers.IO) {
            setAddPhotosSettingsUseCase.setAddPhotosSettings()
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val getAddPhotosSettingsUseCase: IGetAddPhotosSettingsUseCase,
        private val setAddPhotosSettingsUseCase: ISetAddPhotosSettingsUseCase,
        private val getUserUseCase: IGetUserUseCase,
        private val getImagesUseCase: IGetImagesUseCase,
        private val setImageCropUseCase: ISetImageCropUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == UserProfileViewModel::class.java)
            return UserProfileViewModel(
                getAddPhotosSettingsUseCase = getAddPhotosSettingsUseCase,
                setAddPhotosSettingsUseCase = setAddPhotosSettingsUseCase,
                getUserUseCase = getUserUseCase,
                getImagesUseCase = getImagesUseCase,
                setImageCropUseCase = setImageCropUseCase
            ) as T
        }

    }

}