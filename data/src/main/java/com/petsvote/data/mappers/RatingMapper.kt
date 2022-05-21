package com.petsvote.data.mappers

import com.petsvote.domain.entity.pet.RatingFilterLocationType
import com.petsvote.domain.entity.pet.RatingPet
import com.petsvote.domain.entity.pet.RatingPetItemType
import com.petsvote.retrofit.entity.rating.PetRating
import com.petsvote.retrofit.entity.rating.Rating


fun List<PetRating>.remoteToRatingList(): List<RatingPet>{
    var list = mutableListOf<RatingPet>()
    this.onEach { list.add(it.remoteToRating()) }
    return list
}

fun PetRating.remoteToRating(): RatingPet{
    return RatingPet(
        this.index ?: 0,
        this.pet_id,
        this.name,
        this.country_name,
        this.city_name,
        RatingFilterLocationType.CITY,
        this.photos.remoteToPhotoList(),false,
        if(this.index != 1) RatingPetItemType.DEFAULT else RatingPetItemType.TOP
    )
}