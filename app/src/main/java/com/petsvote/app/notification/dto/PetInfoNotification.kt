package com.petsvote.app.notification.dto

import kotlinx.serialization.SerialInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PetInfoNotification(
    var title: String,
    var description: String? = null,
    val vote: Int,
    val pet_id: Int,
    val image: String
)