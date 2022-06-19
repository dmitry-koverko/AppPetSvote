package com.petsvote.user.crop

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.petsvote.core.BaseViewModel
import com.petsvote.domain.entity.user.UserPet
import com.petsvote.domain.usecases.configuration.IGetImagesUseCase
import com.petsvote.domain.usecases.configuration.ISetImageCropUseCase
import com.petsvote.domain.usecases.configuration.ISetImageUseCase
import com.petsvote.domain.usecases.configuration.impl.GetImagesUseCase
import com.petsvote.domain.usecases.configuration.impl.SetImageCropUseCase
import com.petsvote.domain.usecases.configuration.impl.SetImageUseCase
import com.petsvote.domain.usecases.user.IGetUserPetsUseCase
import com.petsvote.domain.usecases.user.IGetUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import javax.inject.Inject

class CropUserImageViewModel @Inject constructor(
    private val getImagesUseCase: IGetImagesUseCase,
    private val setImageCropUseCase: ISetImageCropUseCase,
    private val setImageUseCase: ISetImageUseCase
) : BaseViewModel() {

    var image = MutableStateFlow<Bitmap?>(null)

    fun getImage() {

        viewModelScope.launch(Dispatchers.IO) {

            getImagesUseCase.getImage().collect {
                val imageStream = ByteArrayInputStream(it.image)
                val theImage = BitmapFactory.decodeStream(imageStream)
                image.emit(theImage)
            }

        }

    }

    fun setImage() {
        viewModelScope.launch(Dispatchers.IO) {
            setImageUseCase.setImage(byteArrayOf())
        }
    }

    fun setImageCrop(bytes: ByteArray) {
        viewModelScope.launch(Dispatchers.IO) {
            setImageCropUseCase.setImageCrop(bytes)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val getImagesUseCase: IGetImagesUseCase,
        private val setImageCropUseCase: ISetImageCropUseCase,
        private val setImageUseCase: ISetImageUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == CropUserImageViewModel::class.java)
            return CropUserImageViewModel(
                getImagesUseCase = getImagesUseCase,
                setImageCropUseCase = setImageCropUseCase,
                setImageUseCase = setImageUseCase
            ) as T
        }

    }

}