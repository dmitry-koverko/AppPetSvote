package com.petsvote.retrofit.entity.breeds

import kotlinx.serialization.Serializable

@Serializable
data class Breed(
    val id: Int,
    val title: String,
)