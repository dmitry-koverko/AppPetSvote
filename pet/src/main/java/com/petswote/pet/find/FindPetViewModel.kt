package com.petswote.pet.find

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.petsvote.core.BaseViewModel
import com.petsvote.domain.entity.pet.FindPet
import com.petsvote.domain.entity.pet.PetPhoto
import com.petsvote.domain.entity.user.DataResponse
import com.petsvote.domain.usecases.configuration.IGetAddPhotosSettingsUseCase
import com.petsvote.domain.usecases.configuration.ISetAddPhotosSettingsUseCase
import com.petsvote.domain.usecases.pet.IFindPetUseCase
import com.petsvote.domain.usecases.pet.create.*
import com.petsvote.domain.usecases.pet.impl.FindPetUseCase
import com.petsvote.domain.usecases.user.IGetUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class FindPetViewModel @Inject constructor(
    private val findPetUseCase: IFindPetUseCase
) : BaseViewModel() {

    var progress = MutableStateFlow(false)
    var findPet = MutableStateFlow<FindPet?>(null)
    var state = MutableStateFlow(0) // 0 -default, 1 - showPet, 2 - findPetError

    fun findPet(id: Int) {
        launchIO{
            state.emit(0)
            findPetUseCase.findPet(id).collect {
                when(it){
                    is DataResponse.Loading -> progress.emit(true)
                    is DataResponse.Error -> {
                        progress.emit(false)
                        state.emit(2)
                    }
                    is DataResponse.Success<FindPet> ->{
                        findPet.emit(it.data)
                        state.emit(1)
                        progress.emit(false)
                    }
                    else -> state.emit(0)
                }
            }
        }
    }


    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val findPetUseCase: IFindPetUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == FindPetViewModel::class.java)
            return FindPetViewModel(
                findPetUseCase = findPetUseCase
            ) as T
        }

    }

}