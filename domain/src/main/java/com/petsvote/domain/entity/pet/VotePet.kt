package com.petsvote.domain.entity.pet

import com.petsvote.domain.entity.user.Photo
import kotlinx.serialization.Serializable

@Serializable
data class VotePet(
    val index: Int,
    val pet_id: Int,
    val title: String,
    val description: String,
    val photos: List<String>
)