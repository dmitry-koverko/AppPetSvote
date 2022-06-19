package com.petsvote.user.simple

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.petsvote.core.BaseViewModel
import com.petsvote.domain.entity.user.UserPet
import com.petsvote.domain.usecases.user.IGetUserPetsUseCase
import com.petsvote.domain.usecases.user.IGetUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class SimpleUserViewModel @Inject constructor(
    private val userPetsUserCase: IGetUserPetsUseCase,
    private val getUserUseCase: IGetUserUseCase
) : BaseViewModel() {

    var pets = MutableStateFlow<List<UserPet>>(listOf())
    var ava = MutableStateFlow<String>("")

    fun getPets() {

        viewModelScope.launch(Dispatchers.IO) {
            userPetsUserCase.getUserPets().collect {
                pets.emit(it)
            }
        }

        viewModelScope.launch (Dispatchers.IO) {
            getUserUseCase.getUser().avatar?.let { ava.emit(it) }
        }
    }


    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val userPetsUserCase: IGetUserPetsUseCase,
        private val getUserUseCase: IGetUserUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == SimpleUserViewModel::class.java)
            return SimpleUserViewModel(
                userPetsUserCase = userPetsUserCase,
                getUserUseCase = getUserUseCase
            ) as T
        }

    }

}