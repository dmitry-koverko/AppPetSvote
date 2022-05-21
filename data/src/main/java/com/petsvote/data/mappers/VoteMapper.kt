package com.petsvote.data.mappers

import com.petsvote.domain.entity.filter.RatingFilterType
import com.petsvote.domain.entity.pet.RatingFilterLocationType
import com.petsvote.domain.entity.pet.RatingPet
import com.petsvote.domain.entity.pet.RatingPetItemType
import com.petsvote.domain.entity.pet.VotePet
import com.petsvote.retrofit.entity.rating.PetRating
import com.petsvote.retrofit.entity.rating.Vote
import com.petsvote.retrofit.entity.rating.VoteRating

fun List<VoteRating>.remoteToVotePetsList(): List<VotePet> {
    var list = mutableListOf<VotePet>()
    this.onEach { list.add(it.remoteToVote()) }
    return list
}

fun VoteRating.remoteToVote(): VotePet {
    return VotePet(
        0,
        this.pet_id,
        this.name,
        this.bdate,
        "",
        "",
       0,
        emptyList()
    )
}
