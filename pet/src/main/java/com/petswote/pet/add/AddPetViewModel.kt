package com.petswote.pet.add

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.petsvote.core.BaseViewModel
import com.petsvote.domain.entity.pet.PetPhoto
import com.petsvote.domain.entity.user.SaveUserParams
import com.petsvote.domain.usecases.configuration.IGetAddPhotosSettingsUseCase
import com.petsvote.domain.usecases.configuration.IGetImagesUseCase
import com.petsvote.domain.usecases.configuration.ISetAddPhotosSettingsUseCase
import com.petsvote.domain.usecases.configuration.ISetImageCropUseCase
import com.petsvote.domain.usecases.pet.IGetImagesCropUseCase
import com.petsvote.domain.usecases.pet.IRemovePetImageUseCase
import com.petsvote.domain.usecases.pet.impl.GetImagesCropUseCase
import com.petsvote.domain.usecases.pet.impl.RemovePetImageUseCase
import com.petsvote.domain.usecases.user.IGetUserUseCase
import com.petsvote.domain.usecases.user.impl.ISaveUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import javax.inject.Inject

class AddPetViewModel @Inject constructor(
    private val getUserUseCase: IGetUserUseCase,
    private val getImagesCropUseCase: IGetImagesCropUseCase,
    private val removePetImageUseCase: IRemovePetImageUseCase
) : BaseViewModel() {

    var listPhotosPet: MutableStateFlow<List<PetPhoto>> = MutableStateFlow(emptyList())

    fun getConfiguration() {

        viewModelScope.launch (Dispatchers.IO) {

            var jobUser = async { getUserUseCase.getUser() }.await()

            getImagesCropUseCase.getImagesCrop().collect {
                listPhotosPet.emit(it)
            }
        }
    }

    fun removeItem(id: Int){
        viewModelScope.launch (Dispatchers.IO) {
            removePetImageUseCase.removeImage(id)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val getUserUseCase: IGetUserUseCase,
        private val getImagesCropUseCase: IGetImagesCropUseCase,
        private val removePetImageUseCase: IRemovePetImageUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == AddPetViewModel::class.java)
            return AddPetViewModel(
                getUserUseCase = getUserUseCase,
                getImagesCropUseCase = getImagesCropUseCase,
                removePetImageUseCase = removePetImageUseCase
            ) as T
        }

    }

}