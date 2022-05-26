package com.petsvote.domain.usecases.rating.impl

import com.petsvote.domain.entity.params.AddVoteParams
import com.petsvote.domain.repository.rating.RatingRepository
import com.petsvote.domain.usecases.rating.IAddVoteUseCase
import javax.inject.Inject

class AddVoteUseCase @Inject constructor(
    private val ratingRepository: RatingRepository
): IAddVoteUseCase{
    override suspend fun addVote(params: AddVoteParams) {
        ratingRepository.addVote(params)
    }
}