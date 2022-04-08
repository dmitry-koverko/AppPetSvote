package com.petsvote.room.converters

import androidx.room.TypeConverter
import com.petsvote.room.entity.EntityLocation
import com.petsvote.room.entity.EntityPhoto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class LocationConverter {

    @TypeConverter
    fun locationToString(location: EntityLocation): String {
        return Json.encodeToString(location)
    }

    @TypeConverter
    fun stringToLocation(data: String): EntityLocation {
        return Json.decodeFromString(data)
    }

}