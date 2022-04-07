package com.petsvote.splash

import android.util.Log
import androidx.lifecycle.*
import com.petsvote.core.BaseViewModel
import com.petsvote.core.ext.log
import com.petsvote.domain.usecases.RegisterUserUseCase
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

class SplashViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
) : BaseViewModel() {
    fun get() {
        log("call get function in viewModel")
        viewModelScope.launch {
            val res = registerUserUseCase.getData()
            log(res.toString())
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(private val registerUserUseCase: RegisterUserUseCase)
        : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == SplashViewModel::class.java)
            return SplashViewModel(registerUserUseCase = registerUserUseCase) as T
        }

    }
}