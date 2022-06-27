package com.petsvote.retrofit.entity

import kotlinx.serialization.Serializable

@Serializable
data class InstagramResponse(
    val user: UserInstagram,
    val status: String
)
