package com.petsvote.room.entity.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.petsvote.room.converters.LocationConverter
import com.petsvote.room.converters.UserPetConverter
import com.petsvote.room.entity.EntityLocation
import org.jetbrains.annotations.NotNull

@Entity
data class EntityUserInfo(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = 1,
    var bearer: String?  =null,
    var first_name: String?  =null,
    var has_blocked: Boolean?  =null,
    var last_name: String?  =null,
    var avatar: String?  =null,
    var first_vote: Int?  =null,
    var has_paid_votes: Boolean?  =null,
    var notify_status: Int?  =null,
    var official: Int? =null,

    @NotNull
    @TypeConverters(UserPetConverter::class)
    var pet: List<EntityUserPet> = listOf(),

    @TypeConverters(LocationConverter::class)
    var location: EntityLocation? =null,
)

