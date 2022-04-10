package com.petsvote.domain.entity.user

data class Location(
    var city_id: Int?,
    val country_id: Int?,
    val country: String?,
    val city: String?
)