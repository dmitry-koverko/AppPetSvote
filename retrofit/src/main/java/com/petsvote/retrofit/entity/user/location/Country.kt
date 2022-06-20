package com.petsvote.retrofit.entity.user.location

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val id: Int,
    val title: String
)