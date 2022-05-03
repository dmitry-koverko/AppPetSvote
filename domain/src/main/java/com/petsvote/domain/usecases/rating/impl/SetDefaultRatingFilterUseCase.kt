package com.petsvote.domain.usecases.rating.impl

import com.petsvote.domain.repository.rating.IRatingFilterRepository
import com.petsvote.domain.usecases.rating.ISetDefaultRatingFilterUseCase
import javax.inject.Inject

class SetDefaultRatingFilterUseCase @Inject constructor(
    private val ratingFilterRepository: IRatingFilterRepository
): ISetDefaultRatingFilterUseCase {
    override suspend fun setDefaultRatingFilter() {
        ratingFilterRepository.setDefaultRatingFilter()
    }
}