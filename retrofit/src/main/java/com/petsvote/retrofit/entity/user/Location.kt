package com.petsvote.retrofit.entity.user
import kotlinx.serialization.Serializable

@Serializable
data class Location(
    var city_id: Int,
    var country_id: Int,
    var country: String,
    var city: String
)