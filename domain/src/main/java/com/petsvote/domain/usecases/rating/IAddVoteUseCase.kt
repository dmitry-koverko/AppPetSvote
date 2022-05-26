package com.petsvote.domain.usecases.rating

import com.petsvote.domain.entity.params.AddVoteParams

interface IAddVoteUseCase {

    suspend fun addVote(params: AddVoteParams)

}