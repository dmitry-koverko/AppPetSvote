package com.petsvote.room.converters

import androidx.room.TypeConverter
import com.petsvote.room.entity.EntityPhoto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class PhotoConverter {

    @TypeConverter
    fun photoItemsToString(photos: List<EntityPhoto>): String {
        return Json.encodeToString(photos)
    }

    @TypeConverter
    fun stringToPhotoItems(data: String): List<EntityPhoto> {
        return Json.decodeFromString(data)
    }
}