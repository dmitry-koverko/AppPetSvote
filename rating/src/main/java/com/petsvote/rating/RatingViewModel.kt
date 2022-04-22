package com.petsvote.rating

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.petsvote.core.BaseViewModel
import com.petsvote.domain.entity.user.DataResponse
import com.petsvote.domain.entity.user.RegisterUserParams
import com.petsvote.domain.entity.user.UserInfo
import com.petsvote.domain.usecases.user.RegisterUserUseCase
import com.petsvote.domain.usecases.user.SaveUserToLocalUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class RatingViewModel @Inject constructor() : BaseViewModel() {

    fun getData(){

    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor()
        : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == RatingViewModel::class.java)
            return RatingViewModel() as T
        }

    }
}