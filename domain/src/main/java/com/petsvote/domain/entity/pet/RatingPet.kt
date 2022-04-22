package com.petsvote.domain.entity.pet

import com.petsvote.core.adapter.Item

data class RatingPet(
    val pet_id: Int,
    val isUserPet: Boolean
): Item