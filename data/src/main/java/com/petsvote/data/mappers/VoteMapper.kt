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
        this.pet_id,
        this.name,
        this.bdate,
        this.sex,
        getLocation(this.city_name, this.country_name, ratingType),
       breeds.find { it.id == this.id }?.title ?: "",
        emptyList()
    )
}

fun getLocation(city: String, country: String, ratingType: RatingFilterType?): String {
    return when(ratingType){
        RatingFilterType.GLOBAL -> ",$city, $country"
        RatingFilterType.COUNTRY -> ",$city"
        else -> ""
    }
}
