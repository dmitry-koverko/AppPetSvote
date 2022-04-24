package com.petsvote.domain.entity.pet

import com.petsvote.core.adapter.Item

data class RatingPet(
    val pet_id: Int,
    val isUserPet: Boolean,
    val itemType: RatingPetItemType = RatingPetItemType.DEFAULT
): Item

enum class RatingPetItemType{
    TOP,
    TOPADDPET,
    NULLABLE,
    ADDPET,
    DEFAULT,
}