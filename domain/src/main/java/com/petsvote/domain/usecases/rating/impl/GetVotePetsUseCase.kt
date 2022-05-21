package com.petsvote.domain.usecases.rating.impl

import com.petsvote.domain.entity.pet.VotePet
import com.petsvote.domain.repository.rating.RatingRepository
import com.petsvote.domain.usecases.rating.IGetVotePetsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVotePetsUseCase @Inject constructor(
    private val ratingRepository: RatingRepository
): IGetVotePetsUseCase {

    override suspend fun getRating(offset: Int): Flow<List<VotePet>> {
       return ratingRepository.getVotePets(offset)
    }
}