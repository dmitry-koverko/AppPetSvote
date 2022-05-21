package com.petsvote.retrofit.entity.rating

import kotlinx.serialization.Serializable

@Serializable
data class VoteRating(
    val temp_type: String,
    val id: Int,
    val pet_id: Int,
    val bdate: String,
    val name: String
)