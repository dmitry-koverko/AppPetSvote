package com.petsvote.retrofit.entity.rating
import com.petsvote.retrofit.entity.Photo
import kotlinx.serialization.Serializable

@Serializable
data class Rating(
    val pets: List<PetRating>,
    val lang: String,
    val total_count: Int,
    val offset: Int
)
