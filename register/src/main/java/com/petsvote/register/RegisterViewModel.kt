package com.petsvote.register

import androidx.lifecycle.*
import com.petsvote.core.BaseViewModel
import com.petsvote.domain.entity.user.DataResponse
import com.petsvote.domain.entity.user.RegisterUserParams
import com.petsvote.domain.usecases.user.RegisterUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
) : BaseViewModel() {

    private val isLoading = MutableStateFlow(false)
    val _isLoading = isLoading.asStateFlow()


    fun get(code: String) {
        viewModelScope.launch {
            registerUserUseCase.registerUser(RegisterUserParams(code = code)).onEach {
                when(it){
                    is DataResponse.Loading -> isLoading.value = true
                    is DataResponse.Success -> {
                        isLoading.value = false
                    }
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(private val registerUserUseCase: RegisterUserUseCase)
        : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == RegisterViewModel::class.java)
            return RegisterViewModel(registerUserUseCase = registerUserUseCase) as T
        }

    }
}