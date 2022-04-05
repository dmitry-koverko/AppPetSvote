package com.petsvote.core.ext

import android.util.Log
import androidx.fragment.app.Fragment

fun Fragment.log(message: String){
    Log.d(this::class.java.name, message)
}