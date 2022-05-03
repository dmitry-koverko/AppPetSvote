package com.petsvote.domain.usecases.filter.impl

import com.petsvote.domain.entity.filter.RatingFilterType
import com.petsvote.domain.repository.rating.IRatingFilterRepository
import com.petsvote.domain.usecases.filter.ISetRatingFilterTypeUseCase
import javax.inject.Inject

class SetRatingFilterTypeUseCase @Inject constructor(
    private val ratingFilterRepository: IRatingFilterRepository
):ISetRatingFilterTypeUseCase {

    override suspend fun setRatingFilterType(filterType: RatingFilterType) {
        ratingFilterRepository.setRatingFilterType(filterType)
    }
}