package com.petsvote.retrofit.entity.user.location

import kotlinx.serialization.Serializable

@Serializable
data class City(
    val id: Int,
    val important: Int?,
    val country_id: Int,
    val title: String,
    val region: String?,
    val area: String?,
    val region_id: Int?
)