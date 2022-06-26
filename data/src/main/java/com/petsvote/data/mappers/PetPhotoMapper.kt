package com.petsvote.data.mappers

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.petsvote.domain.entity.pet.PetPhoto
import com.petsvote.room.entity.EnityPetImage
import java.io.ByteArrayInputStream

fun List<EnityPetImage>.toPetPhotoList(): List<PetPhoto>{
    var list: MutableList<PetPhoto> = mutableListOf()
    for (bytes in this){
        list.add(bytes.toPetPhoto())
    }
    return list
}

fun EnityPetImage.toPetPhoto(): PetPhoto{
    return PetPhoto(
        id = this.id,
        bitmap = this.imagePet.toBitmap()
    )
}

fun ByteArray.toBitmap(): Bitmap{
    val imageStream = ByteArrayInputStream(this)
    return BitmapFactory.decodeStream(imageStream)
}