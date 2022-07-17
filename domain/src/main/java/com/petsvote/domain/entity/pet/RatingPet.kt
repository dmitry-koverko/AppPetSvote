package com.petsvote.domain.entity.pet

import com.petsvote.core.adapter.Item
import com.petsvote.domain.entity.user.Photo

data class RatingPet(
    val index: Int,
    val pet_id: Int,
    val name: String,
    val country_name: String,
    val city_name: String,
    var locationType: RatingFilterLocationType = RatingFilterLocationType.WORLD,
    val photos: List<Photo>,
    var isUserPet: Boolean,
    val itemType: RatingPetItemType = RatingPetItemType.DEFAULT,
    var breed_id: Int,
    var type: String,
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