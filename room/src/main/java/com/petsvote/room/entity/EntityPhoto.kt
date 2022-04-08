package com.petsvote.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EntityPhoto(
    @PrimaryKey (autoGenerate = false)
    val id: Int,
    val num: Int,
    val url: String
)