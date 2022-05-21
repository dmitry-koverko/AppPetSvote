package com.petsvote.domain.entity.pet

import com.petsvote.domain.entity.user.Photo
import kotlinx.serialization.Serializable

@Serializable
data class VotePet(
    val index: Int,
    val pet_id: Int,
    val name: String,
    val bdate: String,
    val sex: String,
    val location: String,
    val breedId: Int,
    val photos: List<String>
)