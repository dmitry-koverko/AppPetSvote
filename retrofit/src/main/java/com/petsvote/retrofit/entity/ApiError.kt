package com.petsvote.retrofit.entity

import kotlinx.serialization.Serializable

@Serializable
data class ApiError(
    val status: String,
    val message: String
)