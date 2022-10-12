package com.petsvote.domain.usecases.rating.impl

import com.petsvote.domain.entity.pet.VotePet
import com.petsvote.domain.repository.rating.RatingRepository
import com.petsvote.domain.usecases.rating.IGetVotePetsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetVotePetsUseCase @Inject constructor(
    private val ratingRepository: RatingRepository
): IGetVotePetsUseCase {

    override suspend fun getRating(): Flow<List<VotePet>> {
       return ratingRepository.getVotePets()
    }
}