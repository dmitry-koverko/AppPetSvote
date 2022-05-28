package com.petsvote.retrofit.entity.user
import com.petsvote.retrofit.entity.Photo
import kotlinx.serialization.Serializable

@Serializable
data class UserPet(
    val id: Int? = null,
    val pet_id: Int? = null,
    val name: String? = "",
    val first_name: String? = "",
    val last_name: String? = "",
    val avatar: String? = "",
    val official: Int? = -1,
    val global_range: Int? = -1,
    val country_range: Int? = -1,
    val city_range: Int? = -1,
    val global_score: Int? = -1,
    val global_dynamic: Int? = -1,
    val country_dynamic: Int? = -1,
    val city_dynamic: Int? = -1,
    val mark_dynamic: Int? = -1,
    val has_paid_votes: Int? = -1,
    val bdate: String? = "",
    val sex: String?,
    val photos: List<Photo>? = listOf()
)