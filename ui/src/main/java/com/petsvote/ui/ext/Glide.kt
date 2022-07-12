package com.petsvote.ui.ext

import android.graphics.drawable.Drawable
import android.media.Image
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestListener

fun ImageView.loadUrl(url: String){
    Glide
        .with(this)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.DATA)
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

fun ImageView.loadUrlListeners(url: String, listener: RequestListener<Drawable>){
    Glide
        .with(this)
        .load(url)
        .centerCrop()
        .listener(listener)
        .into(this);
}