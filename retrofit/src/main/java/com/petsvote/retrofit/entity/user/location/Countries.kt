package com.petsvote.retrofit.entity.user.location

import kotlinx.serialization.Serializable

@Serializable
data class Countries(
    val lang: String,
    val countries: List<Country>,
)