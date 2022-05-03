package com.petsvote.domain.usecases.filter.impl

import com.petsvote.domain.repository.rating.IRatingFilterRepository
import com.petsvote.domain.usecases.filter.ISetBreedIdInRatingFilterUseCase
import javax.inject.Inject

class SetBreedIdInRatingFilterUseCase @Inject constructor(
    private val ratingFilterRepository: IRatingFilterRepository
): ISetBreedIdInRatingFilterUseCase {

    override suspend fun setBredIdRatingFilter(breedId: Int?) {
       ratingFilterRepository.setBredIdRatingFilter(breedId = breedId)
    }
}