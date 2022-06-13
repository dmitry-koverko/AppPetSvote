package com.petsvote.user.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.petsvote.core.BaseViewModel
import com.petsvote.domain.entity.user.UserInfo
import com.petsvote.domain.entity.user.UserPet
import com.petsvote.domain.usecases.configuration.IGetSettingsNotifyUseCase
import com.petsvote.domain.usecases.configuration.ISetSettingsNotifyUseCase
import com.petsvote.domain.usecases.user.IGetUserPetsUseCase
import com.petsvote.domain.usecases.user.IGetUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsProfileViewModel @Inject constructor(
    private val getUserUseCase: IGetUserUseCase,
    private val settingsNotifyUseCase: IGetSettingsNotifyUseCase,
    private val setSettingsNotifyUseCase: ISetSettingsNotifyUseCase
) : BaseViewModel() {

    var user = MutableStateFlow<UserInfo?>(null)
    var settingsNotify = MutableStateFlow<Boolean>(false)

    fun getUserInfo() {
        viewModelScope.launch (Dispatchers.IO) {
            user.emit(getUserUseCase.getUser())
        }
    }

    fun getNotify(){
        viewModelScope.launch (Dispatchers.IO) {
            settingsNotify.emit(settingsNotifyUseCase.getUserNotify())
        }
    }

    fun setNotify(b: Boolean){
        viewModelScope.launch {
            setSettingsNotifyUseCase.setNotify(b)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val getUserUseCase: IGetUserUseCase,
        private val settingsNotifyUseCase: IGetSettingsNotifyUseCase,
        private val setSettingsNotifyUseCase: ISetSettingsNotifyUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == SettingsProfileViewModel::class.java)
            return SettingsProfileViewModel(
                getUserUseCase = getUserUseCase,
                settingsNotifyUseCase = settingsNotifyUseCase,
                setSettingsNotifyUseCase = setSettingsNotifyUseCase
            ) as T
        }

    }

}