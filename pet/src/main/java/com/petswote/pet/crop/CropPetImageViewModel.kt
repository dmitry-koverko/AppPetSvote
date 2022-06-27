package com.petswote.pet.crop

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.petsvote.core.BaseViewModel
import com.petsvote.domain.usecases.pet.create.IAddImageCropUseCase
import com.petsvote.domain.usecases.pet.create.IGetImagePetProfileUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import javax.inject.Inject

class CropPetImageViewModel @Inject constructor(
    private val getImagePetProfileUseCase: IGetImagePetProfileUseCase,
    private val addImageCropUseCase: IAddImageCropUseCase
) : BaseViewModel() {

    var image = MutableStateFlow<Bitmap?>(null)

    fun getImage() {

        viewModelScope.launch(Dispatchers.IO) {

            getImagePetProfileUseCase.getImagePetProfile().collect {
                val imageStream = ByteArrayInputStream(it)
                val theImage = BitmapFactory.decodeStream(imageStream)
                image.emit(theImage)
            }

        }

    }

    fun setImageCrop(bytes: ByteArray) {
        viewModelScope.launch(Dispatchers.IO) {
            addImageCropUseCase.adImage(bytes)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val getImagePetProfileUseCase: IGetImagePetProfileUseCase,
        private val addImageCropUseCase: IAddImageCropUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == CropPetImageViewModel::class.java)
            return CropPetImageViewModel(
                getImagePetProfileUseCase = getImagePetProfileUseCase,
                addImageCropUseCase = addImageCropUseCase
            ) as T
        }

    }

}