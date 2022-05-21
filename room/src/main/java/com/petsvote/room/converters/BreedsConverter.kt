package com.petsvote.room.converters

import androidx.room.TypeConverter
import com.petsvote.room.entity.EntityBreed
import com.petsvote.room.entity.EntityPhoto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class BreedsConverter {

    @TypeConverter
    fun breedsItemsToString(photos: List<EntityBreed>): String {
        return Json.encodeToString(photos)
    }

    @TypeConverter
    fun stringToBreedsItems(data: String): List<EntityBreed> {
        return Json.decodeFromString(data)
    }

}