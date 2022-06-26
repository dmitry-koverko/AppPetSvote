package com.petsvote.room.converters

import androidx.room.TypeConverter
import com.petsvote.room.entity.EnityPetImage
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class PetImageConverter {

    @TypeConverter
    fun breedsItemsToString(photos: List<EnityPetImage>): String {
        return Json.encodeToString(photos)
    }

    @TypeConverter
    fun stringToBreedsItems(data: String): List<EnityPetImage> {
        return Json.decodeFromString(data)
    }

}