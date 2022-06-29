package com.petsvote.domain.entity.pet
import kotlinx.serialization.Serializable

data class FindPet(
    val pet: Pet,
    val vote: Int? = null
)