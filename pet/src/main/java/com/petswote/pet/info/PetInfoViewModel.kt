package com.petswote.pet.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.petsvote.core.BaseViewModel
import com.petsvote.domain.entity.pet.FindPet
import com.petsvote.domain.entity.pet.Pet
import com.petsvote.domain.entity.pet.PetDetails
import com.petsvote.domain.entity.user.DataResponse
import com.petsvote.domain.usecases.configuration.impl.GetBreedsUseCase
import com.petsvote.domain.usecases.pet.IFindPetUseCase
import com.petsvote.domain.usecases.pet.IGetBreedByIdUseCase
import com.petsvote.domain.usecases.pet.IGetPetDetailsUseCase
import com.petsvote.domain.usecases.pet.impl.GetBreedByIdUseCase
import com.petswote.pet.find.FindPetViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class PetInfoViewModel @Inject constructor(
    private val petDetailsUseCase: IGetPetDetailsUseCase,
    private val findPetUseCase: IFindPetUseCase,
    private val getBreedByIdUseCase: IGetBreedByIdUseCase
): BaseViewModel() {

    var uiPet = MutableStateFlow<FindPet?>(null)
    var uiPetDetails = MutableStateFlow<PetDetails?>(null)
    var uiBreedString = MutableStateFlow<String>("")

    fun getPetInfo(petId: Int) {
        launchIO {
            findPetUseCase.findPet(petId).collect {
                when (it) {
                    is DataResponse.Success<FindPet> -> {
                        uiPet.emit(it.data)
                        uiBreedString.emit(getBreedByIdUseCase.getBreedInfo(it.data.pet.breed_id))
                        var petDetails = petDetailsUseCase.getPetDetails(it.data.pet.id)
                        uiPetDetails.emit(petDetails)
                    }
                }
            }
        }
    }


    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val petDetailsUseCase: IGetPetDetailsUseCase,
        private val findPetUseCase: IFindPetUseCase,
        private val getBreedByIdUseCase: IGetBreedByIdUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == PetInfoViewModel::class.java)
            return PetInfoViewModel(
                petDetailsUseCase = petDetailsUseCase,
                findPetUseCase = findPetUseCase,
                getBreedByIdUseCase = getBreedByIdUseCase
            ) as T
        }

    }

}