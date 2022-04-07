package com.petsvote.retrofit.entity
import kotlinx.serialization.Serializable

@Serializable
data class Photo(
    val id: Int,
    val num: Int,
    val url: String
)