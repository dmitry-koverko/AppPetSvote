package com.petsvote.dialog.enity

import android.graphics.Bitmap
import android.net.Uri

data class LocalPhoto(
    val bitmap: Uri?,
    val bitmapObject: Bitmap? = null
)