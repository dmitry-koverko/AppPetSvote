package com.petsvote.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class EntityBreed(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var lang: String,
    var type: String,
    var id_breed: Int,
    var title: String
)