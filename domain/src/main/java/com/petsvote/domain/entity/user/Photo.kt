package com.petsvote.domain.entity.user

import kotlinx.serialization.Serializable

@Serializable
data class Photo(
    val id: Int,
    val num: Int,
    val url: String
)