package com.petsvote.core.ext

import android.util.Log
import androidx.lifecycle.ViewModel

fun ViewModel.log(message: String){
    Log.d(this::class.java.name, message)
}