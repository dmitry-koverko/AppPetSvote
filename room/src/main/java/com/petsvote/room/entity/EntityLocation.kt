package com.petsvote.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EntityLocation(
    @PrimaryKey
    var city_id: Int? = null,
    val country_id: Int? = null,
    val country: String? = null,
    val city: String? = null
)

