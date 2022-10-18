package com.petsvote.app.notification.dto

import kotlinx.serialization.Serializable

data class PetNotification (
    var body: String,
    var title: String
)
