package com.petsvote.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction0

open class BaseViewModel: ViewModel() {

    fun launchIn(on: KSuspendFunction0<Unit>? = null) = viewModelScope.launch (Dispatchers.IO){
        on?.invoke()
    }

}