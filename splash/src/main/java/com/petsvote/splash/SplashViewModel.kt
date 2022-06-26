package com.petsvote.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.petsvote.core.BaseViewModel
import com.petsvote.domain.usecases.configuration.IGetBreedsUseCase
import com.petsvote.domain.usecases.pet.ISetEmptyPetProfileUseCase
import com.petsvote.domain.usecases.pet.impl.SetEmptyPetProfileUseCase
import com.petsvote.domain.usecases.user.ICheckLoginUserUseCase
import com.petsvote.domain.usecases.user.IGetCurrentUserUseCase
import com.petsvote.domain.usecases.user.ISetEmptyUserProfileUseCase
import com.petsvote.domain.usecases.user.impl.GetCurrentUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val checkLoginUserUseCase: ICheckLoginUserUseCase,
    private val getCurrentUserUseCase: IGetCurrentUserUseCase,
    private val breedsUseCase: IGetBreedsUseCase,
    private val setEmptyUserProfileUseCase: ISetEmptyUserProfileUseCase,
    private val setEmptyPetProfileUseCase: ISetEmptyPetProfileUseCase
) : BaseViewModel() {

    private var isLoginUser = MutableStateFlow<Boolean?>(null)
    var _isLoginUser = isLoginUser.asStateFlow()

    suspend fun checkLogin() = withContext(Dispatchers.IO) {
        launch {
            setEmptyUserProfileUseCase.setEmptyUserProfile()
        }

        launch {
            setEmptyPetProfileUseCase.setEmptyPetProfile()
        }

        val checkLogin = async {
            checkLoginUserUseCase.checkLoginUser()
        }

        val updateBreeds = async {
            breedsUseCase.updateBreeds()
        }
        updateBreeds.await()
        val isLogin = checkLogin.await()


        if (isLogin) {
            val userJob = async {
                getCurrentUserUseCase.getCurrentUser()
            }
            val user = userJob.await()
        }
        isLoginUser.value = isLogin
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val checkLoginUserUseCase: ICheckLoginUserUseCase,
        private val getCurrentUserUseCase: IGetCurrentUserUseCase,
        private val breedsUseCase: IGetBreedsUseCase,
        private val setEmptyUserProfileUseCase: ISetEmptyUserProfileUseCase,
        private val setEmptyPetProfileUseCase: ISetEmptyPetProfileUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == SplashViewModel::class.java)
            return SplashViewModel(
                checkLoginUserUseCase = checkLoginUserUseCase,
                getCurrentUserUseCase = getCurrentUserUseCase,
                breedsUseCase = breedsUseCase,
                setEmptyUserProfileUseCase = setEmptyUserProfileUseCase,
                setEmptyPetProfileUseCase = setEmptyPetProfileUseCase
            ) as T
        }

    }
}