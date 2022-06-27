package com.petswote.pet.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.petsvote.core.BaseViewModel
import com.petsvote.core.adapter.Item
import com.petsvote.domain.entity.breed.Breed
import com.petsvote.domain.entity.pet.PetPhoto
import com.petsvote.domain.usecases.configuration.IGetAddPhotosSettingsUseCase
import com.petsvote.domain.usecases.configuration.ISetAddPhotosSettingsUseCase
import com.petsvote.domain.usecases.pet.create.*
import com.petsvote.domain.usecases.user.IGetUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddPetViewModel @Inject constructor(
    private val getUserUseCase: IGetUserUseCase,
    private val getImagesCropUseCase: IGetImagesCropUseCase,
    private val removePetImageUseCase: IRemovePetImageUseCase,
    private val getAddPhotosSettingsUseCase: IGetAddPhotosSettingsUseCase,
    private val setAddPhotosSettingsUseCase: ISetAddPhotosSettingsUseCase,
    private val setNamePetUseCase: ISetPetNameUseCase,
    private val getKindPetUseCase: IGetKindPetUseCase,
    private val getBreedPetUseCase: IGetBreedPetUseCase,
    private val setBirthdayPetUseCase: ISetBirthdayPetUseCase,
    private val setSexPetUseCase: ISetSexPetUseCase,
    private val getInstagramUserNameUseCase: IGetInstagramUserNameUseCase
) : BaseViewModel() {

    var listPhotosPet: MutableStateFlow<List<PetPhoto>> = MutableStateFlow(emptyList())
    var isAddPhoto: MutableStateFlow<Boolean> = MutableStateFlow(true)
    var kindTitle: MutableStateFlow<String> = MutableStateFlow("")
    var kindId: MutableStateFlow<Int?> = MutableStateFlow(null)
    var breedTitle: MutableStateFlow<String> = MutableStateFlow("")
    var instagramUserName = MutableStateFlow("")
    var isLoading = MutableStateFlow(false)

    fun getUserName(id: Long) {
        isLoading.value = true
        viewModelScope.launch {
            var res = getInstagramUserNameUseCase.getUsername(id)
            res?.let {
                instagramUserName.value = it
            }
            isLoading.value = false
        }
    }

    fun getConfiguration() {

        viewModelScope.launch(Dispatchers.IO) {
            isAddPhoto.emit(getAddPhotosSettingsUseCase.getAddPhotosSettings())
        }

        viewModelScope.launch (Dispatchers.IO) {

            getImagesCropUseCase.getImagesCrop().collect {
                listPhotosPet.emit(it)
            }

        }
        launchIn(::getKindTitle)
        launchIn(::getBreedTitle)
    }

    suspend fun getBreedTitle(){
        breedTitle.emit(getBreedPetUseCase.getBreed())
    }

    suspend fun getKindTitle(){
        kindId.emit(getKindPetUseCase.getKind()?.id)
        kindTitle.emit(getKindPetUseCase.getKind()?.title ?: "")
    }

    fun removeItem(id: Int){
        viewModelScope.launch (Dispatchers.IO) {
            removePetImageUseCase.removeImage(id)
        }
    }

    fun setAddPhotoDialog(){
        viewModelScope.launch (Dispatchers.IO) {
            setAddPhotosSettingsUseCase.setAddPhotosSettings()
        }
    }

    fun setNamePet(name: String){
        viewModelScope.launch (Dispatchers.IO) {
            setNamePetUseCase.setPetName(name)
        }
    }

    fun setBirthdayPet(birthday: String){
        viewModelScope.launch (Dispatchers.IO) {
            setBirthdayPetUseCase.setBirthdayPet(birthday)
        }
    }

    fun setSexPet(sex: Int){
        viewModelScope.launch (Dispatchers.IO) {
            setSexPetUseCase.setSexPet(sex)
        }
    }



    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val getUserUseCase: IGetUserUseCase,
        private val getImagesCropUseCase: IGetImagesCropUseCase,
        private val removePetImageUseCase: IRemovePetImageUseCase,
        private val getAddPhotosSettingsUseCase: IGetAddPhotosSettingsUseCase,
        private val setAddPhotosSettingsUseCase: ISetAddPhotosSettingsUseCase,
        private val setNamePetUseCase: ISetPetNameUseCase,
        private val getKindPetUseCase: IGetKindPetUseCase,
        private val getBreedPetUseCase: IGetBreedPetUseCase,
        private val setBirthdayPetUseCase: ISetBirthdayPetUseCase,
        private val setSexPetUseCase: ISetSexPetUseCase,
        private val getInstagramUserNameUseCase: IGetInstagramUserNameUseCase

    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == AddPetViewModel::class.java)
            return AddPetViewModel(
                getUserUseCase = getUserUseCase,
                getImagesCropUseCase = getImagesCropUseCase,
                removePetImageUseCase = removePetImageUseCase,
                getAddPhotosSettingsUseCase = getAddPhotosSettingsUseCase,
                setAddPhotosSettingsUseCase = setAddPhotosSettingsUseCase,
                setNamePetUseCase = setNamePetUseCase,
                getKindPetUseCase = getKindPetUseCase,
                getBreedPetUseCase = getBreedPetUseCase,
                setBirthdayPetUseCase = setBirthdayPetUseCase,
                setSexPetUseCase = setSexPetUseCase,
                getInstagramUserNameUseCase = getInstagramUserNameUseCase
            ) as T
        }

    }

}