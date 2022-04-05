package com.petsvote.ui.ext

import android.media.Image
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadUrl(url: String){
    Glide
        .with(this)
        .load(url)
        .centerCrop()
        .into(this);
}

fun ImageView.loadFromResources(id: Int){
    Glide
        .with(this)
        .load(id)
        .centerCrop()
        .into(this);
}