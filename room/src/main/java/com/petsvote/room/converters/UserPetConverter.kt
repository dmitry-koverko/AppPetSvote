package com.petsvote.room.converters

import androidx.room.TypeConverter
import com.petsvote.room.entity.EntityPhoto
import com.petsvote.room.entity.user.EntityUserPet
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class UserPetConverter {

    @TypeConverter
    fun userPetsToString(pets: List<EntityUserPet>): String {
        return Json.encodeToString(pets)
    }

    @TypeConverter
    fun stringToUserPets(data: String): List<EntityUserPet> {
        return Json.decodeFromString(data)
    }
}