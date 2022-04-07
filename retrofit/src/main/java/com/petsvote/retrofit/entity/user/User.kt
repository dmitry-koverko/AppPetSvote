package com.petsvote.retrofit.entity.user
import kotlinx.serialization.Serializable

@Serializable
data class User(
    //val is_blocked: Boolean,
    val location: Location? = null,
    val pets: List<UserPet>? = listOf(),
    val id: Int? = -1,
    //val bearer: String,
    var first_name: String? = "",
    //val has_blocked: Boolean,
    var last_name: String? = "",
    val avatar: String? = "",
    val first_vote: Int? = -1,
    //val has_paid_votes: Boolean,
    //val notify_status: Int,
    val official: Int? = 0
)