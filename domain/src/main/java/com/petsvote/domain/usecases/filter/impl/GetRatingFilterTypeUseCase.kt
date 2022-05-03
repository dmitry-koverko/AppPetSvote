package com.petsvote.domain.usecases.filter.impl

import com.petsvote.domain.entity.filter.RatingFilterType
import com.petsvote.domain.repository.rating.IRatingFilterRepository
import com.petsvote.domain.usecases.filter.IGetRatingFilterTypeUseCase
import javax.inject.Inject

class GetRatingFilterTypeUseCase @Inject constructor(
    private val ratingFilterRepository: IRatingFilterRepository
): IGetRatingFilterTypeUseCase {

    override suspend fun getRatingFilterType(): RatingFilterType {
        return ratingFilterRepository.getSimpleRatingFilter().rating_type ?: RatingFilterType.GLOBAL
    }
}