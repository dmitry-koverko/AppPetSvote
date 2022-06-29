package com.petsvote.retrofit.entity.pet

import kotlinx.serialization.Serializable

@Serializable
data class FindPet(
    val pet: Pet,
    val vote: Int? = null
)