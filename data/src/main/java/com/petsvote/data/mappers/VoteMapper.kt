package com.petsvote.data.mappers

import com.petsvote.domain.entity.filter.RatingFilterType
import com.petsvote.domain.entity.pet.RatingFilterLocationType
import com.petsvote.domain.entity.pet.RatingPet
import com.petsvote.domain.entity.pet.RatingPetItemType
import com.petsvote.domain.entity.pet.VotePet
import com.petsvote.retrofit.entity.rating.PetRating
import com.petsvote.retrofit.entity.rating.Vote
import com.petsvote.retrofit.entity.rating.VoteRating
import com.petsvote.room.entity.EntityBreed

fun List<VoteRating>.remoteToVotePetsList(ratingType: RatingFilterType?, breeds: List<EntityBreed>): List<VotePet> {
    var list = mutableListOf<VotePet>()
    this.onEach { list.add(it.remoteToVote(ratingType, breeds)) }
    return list
}

fun VoteRating.remoteToVote(ratingType: RatingFilterType?, breeds: List<EntityBreed>): VotePet {
    return VotePet(
        0,
        this.id,
        this.pet_id,
        this.name,
        this.bdate,
        getSex(this.sex),
        getLocation(this.city_name, this.country_name, ratingType),
       breeds.find { it.id_breed == this.breed_id }?.title ?: "",
        breed_id = this.breed_id,
        this.type,
        this.user_id,
        this.card_type ?: 0,
        this.photos.remoteToPhotoListString()
    )
}

fun getLocation(city: String, country: String, ratingType: RatingFilterType?): String {
    return when(ratingType){
        RatingFilterType.GLOBAL -> "$city, $country"
        RatingFilterType.COUNTRY -> "$city"
        else -> ""
    }
}


fun getSex(sex: String): Int{
    return if(sex == "FEMALE") 0 else 1
}

