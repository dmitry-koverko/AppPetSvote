package com.petsvote.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.petsvote.core.BaseViewModel
import com.petsvote.domain.entity.user.UserPet
import com.petsvote.domain.usecases.configuration.IGetAddPhotosSettingsUseCase
import com.petsvote.domain.usecases.configuration.ISetAddPhotosSettingsUseCase
import com.petsvote.domain.usecases.user.IGetUserPetsUseCase
import com.petsvote.domain.usecases.user.IGetUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserProfileViewModel @Inject constructor(
    private val getAddPhotosSettingsUseCase: IGetAddPhotosSettingsUseCase,
    private val setAddPhotosSettingsUseCase: ISetAddPhotosSettingsUseCase,
    private val getUserUseCase: IGetUserUseCase
) : BaseViewModel() {

    var isAddPhoto = MutableStateFlow<Boolean>(true)

    fun getConfiguration() {

        viewModelScope.launch(Dispatchers.IO) {
            isAddPhoto.emit(getAddPhotosSettingsUseCase.getAddPhotosSettings())
        }
//
//        viewModelScope.launch (Dispatchers.IO) {
//            getUserUseCase.getUser().avatar?.let { ava.emit(it) }
//        }
    }

    fun setAddPhotoDialog(){
        viewModelScope.launch (Dispatchers.IO) {
            setAddPhotosSettingsUseCase.setAddPhotosSettings()
        }
    }


    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val getAddPhotosSettingsUseCase: IGetAddPhotosSettingsUseCase,
        private val setAddPhotosSettingsUseCase: ISetAddPhotosSettingsUseCase,
        private val getUserUseCase: IGetUserUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == UserProfileViewModel::class.java)
            return UserProfileViewModel(
                getAddPhotosSettingsUseCase = getAddPhotosSettingsUseCase,
                setAddPhotosSettingsUseCase = setAddPhotosSettingsUseCase,
                getUserUseCase = getUserUseCase
            ) as T
        }

    }

}