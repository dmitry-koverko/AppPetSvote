package com.petsvote.ui.ext

import androidx.core.content.ContextCompat
import com.petsvote.ui.BesieLayout
import com.petsvote.ui.R

fun BesieLayout.enabled(){
    this.dotColor = ContextCompat.getColor(this.context, R.color.ui_primary)
    this.click = true
}

fun BesieLayout.disabled(){
    this.dotColor = ContextCompat.getColor(this.context, R.color.disable_btn)
    this.click = false
}