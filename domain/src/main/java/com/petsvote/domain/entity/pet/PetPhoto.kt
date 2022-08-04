package com.petsvote.domain.entity.pet

import android.graphics.Bitmap

data class PetPhoto(
    var id: Int? = null,
    var bitmap: Bitmap? = null,
    var image: String? = null,
    var num: Int? = null
)