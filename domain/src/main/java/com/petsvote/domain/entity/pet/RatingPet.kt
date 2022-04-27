package com.petsvote.domain.entity.pet

import com.petsvote.core.adapter.Item
import com.petsvote.domain.entity.user.Photo

data class RatingPet(
    val pet_id: Int,
    val name: String,
    val country_name: String,
    val city_name: String,
    val locationType: RatingFilterLocationType = RatingFilterLocationType.WORLD,
    val photos: List<Photo>,
    val isUserPet: Boolean,
    val itemType: RatingPetItemType = RatingPetItemType.DEFAULT
): Item

enum class RatingFilterLocationType{
    CITY,
    COUNTRY,
    WORLD
}

enum class RatingPetItemType{
    TOP,
    TOPADDPET,
    NULLABLE,
    ADDPET,
    DEFAULT,
}