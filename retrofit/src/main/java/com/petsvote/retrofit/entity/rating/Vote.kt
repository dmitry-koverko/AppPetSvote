package com.petsvote.retrofit.entity.rating

import kotlinx.serialization.Serializable

@Serializable
data class Vote (
    val pets: List<VoteRating>,
    val lang: String,
    val total_count: Int,
    val offset: Int
)