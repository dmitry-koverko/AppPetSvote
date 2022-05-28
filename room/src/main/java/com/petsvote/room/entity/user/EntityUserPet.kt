package com.petsvote.room.entity.user

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.petsvote.room.converters.PhotoConverter
import com.petsvote.room.entity.EntityPhoto
import kotlinx.serialization.Serializable
import org.jetbrains.annotations.NotNull

@Entity
@Serializable
data class EntityUserPet(

    @PrimaryKey(autoGenerate = true)
    var idPet: Int? = 1,

    @Ignore
    val id: Int? = 1,

    @Ignore
    val name: String? = "",
    @Ignore
    var pets_id: Int? = 1,
    @Ignore
    val global_range: Int? = null,
    @Ignore
    val country_range: Int? = null,
    @Ignore
    val city_range: Int? = null,
    @Ignore
    val global_score: Int? = null,
    @Ignore
    val global_dynamic: Int? = null,
    @Ignore
    val country_dynamic: Int? = null,
    @Ignore
    val city_dynamic: Int? = null,
    @Ignore
    val mark_dynamic: Int? = null,

    @Ignore
    val has_paid_votes: Int? = null,

    @NotNull
    @Ignore
    @TypeConverters(PhotoConverter::class)
    var photos: List<EntityPhoto>? = listOf(),

    @Ignore
    val bdate: String? = null,

    @Ignore
    val sex: String? = null,
)