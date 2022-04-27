package com.petsvote.data.mappers

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
        this.pet_id, false, RatingPetItemType.DEFAULT
    )
}