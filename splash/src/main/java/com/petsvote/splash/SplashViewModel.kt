package com.petsvote.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.petsvote.core.BaseViewModel
import com.petsvote.core.ext.log
import com.petsvote.domain.usecases.user.CheckLoginUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val checkLoginUserUseCase: CheckLoginUserUseCase
) : BaseViewModel() {

    private var isLoginUser = MutableStateFlow<Boolean?>(null)
    var _isLoginUser = isLoginUser.asStateFlow()

    suspend fun checkLogin() = withContext(Dispatchers.IO) {

        val checkLogin = async {
            checkLoginUserUseCase.checkLoginUser()
        }
        val isLogin = checkLogin.await()

        if(!isLogin) isLoginUser.value = false
        else{

        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(private val checkLoginUserUseCase: CheckLoginUserUseCase)
        : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == SplashViewModel::class.java)
            return SplashViewModel(checkLoginUserUseCase = checkLoginUserUseCase) as T
        }

    }
}