package com.petsvote.register

import androidx.lifecycle.*
import com.petsvote.core.BaseViewModel
import com.petsvote.domain.entity.user.DataResponse
import com.petsvote.domain.entity.user.RegisterUserParams
import com.petsvote.domain.entity.user.UserInfo
import com.petsvote.domain.usecases.user.IRegisterUserUseCase
import com.petsvote.domain.usecases.user.ISaveUserToLocalUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val IRegisterUserUseCase: IRegisterUserUseCase,
    private val ISaveUserToLocalUseCase: ISaveUserToLocalUseCase
) : BaseViewModel() {

    private val isLoading = MutableStateFlow(false)
    val _isLoading = isLoading.asStateFlow()

    private val isRegister = MutableStateFlow(false)
    val _isRegister = isRegister.asStateFlow()

    fun registerUser(code: String) {
        viewModelScope.launch {
            IRegisterUserUseCase.registerUser(RegisterUserParams(code = code)).collect {
                when(it){
                    is DataResponse.Loading -> isLoading.value = true
                    is DataResponse.Success<UserInfo> -> {
                        ISaveUserToLocalUseCase.saveUserToLocal(it.data)
                        isRegister.value = true
                    }
                    is DataResponse.Error -> isLoading.value = false
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val IRegisterUserUseCase: IRegisterUserUseCase,
        private val ISaveUserToLocalUseCase: ISaveUserToLocalUseCase
    )
        : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == RegisterViewModel::class.java)
            return RegisterViewModel(
                IRegisterUserUseCase = IRegisterUserUseCase,
                ISaveUserToLocalUseCase = ISaveUserToLocalUseCase
                ) as T
        }

    }
}