package com.petsvote.core.ext

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.petsvote.core.R

fun Fragment.log(message: String){
    Log.d(this::class.java.name, message)
}

fun ViewBinding.stateLoading(args: Array<View> = emptyArray()){
    for(view in args) view.visibility = View.GONE
    this.root.visibility = View.GONE
    try{
        this.root.findViewById<ProgressBar>(R.id.progress).visibility = View.VISIBLE
    }catch (e: Exception){
        Throwable("can not find id progress in binding!")
    }
}