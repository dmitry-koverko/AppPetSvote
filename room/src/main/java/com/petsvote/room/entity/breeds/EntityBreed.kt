package com.petsvote.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class EntityBreed(
    var lang: String,
    var type: String,
    @PrimaryKey(autoGenerate = true)
    var id_breed: Int,
    var title: String
)