package com.petsvote.retrofit.entity.pet
import kotlinx.serialization.Serializable

@Serializable
data class PetDetails(
    val first_name: String,
    val last_name: String,
    val avatar: String,
    val official: Int,
    val global_range: Int,
    val country_range: Int,
    val city_range: Int,
    val global_score: Int,
    val global_dynamic: Int,
    val country_dynamic: Int,
    val city_dynamic: Int,
    val mark_dynamic: Int,
    val has_paid_votes: Int,
    var breedName: String? = null
)