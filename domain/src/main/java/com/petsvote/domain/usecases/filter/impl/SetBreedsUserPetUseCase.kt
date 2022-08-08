package com.petsvote.domain.usecases.filter.impl

import com.petsvote.domain.repository.rating.IRatingFilterRepository
import com.petsvote.domain.usecases.filter.ISetBreedsUserPetUseCase
import javax.inject.Inject

class SetBreedsUserPetUseCase @Inject constructor(
    private val ratingFilterRepository: IRatingFilterRepository
): ISetBreedsUserPetUseCase {

    override suspend fun setUserBredIdRatingFilter(breedId: Int?) {
        ratingFilterRepository.setUserBredIdRatingFilter(breedId = breedId)
    }
}