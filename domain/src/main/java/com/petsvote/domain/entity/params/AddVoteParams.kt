package com.petsvote.domain.entity.params

data class AddVoteParams(
    val mark :Int?,
    val to_pet_id: Int?,
    val first_vote: Boolean?,
    val name: String?,
    val id: Int?
)