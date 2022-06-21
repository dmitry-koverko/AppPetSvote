package com.petsvote.retrofit.entity.user.location

import kotlinx.serialization.Serializable

@Serializable
data class Cities(
    val cities: List<City>,
    val lang: String,
    val total_count: Int
)