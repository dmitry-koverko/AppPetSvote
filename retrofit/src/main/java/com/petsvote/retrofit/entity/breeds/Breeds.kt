package com.petsvote.retrofit.entity.breeds

import kotlinx.serialization.Serializable

@Serializable
data class Breeds(
    val lang: String,
    val version: String,
    val type: String,
    val breeds: List<Breed>
)