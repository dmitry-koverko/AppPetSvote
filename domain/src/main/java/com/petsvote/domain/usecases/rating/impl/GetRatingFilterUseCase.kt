package com.petsvote.domain.usecases.rating.impl

import com.petsvote.domain.entity.filter.RatingFilter
import com.petsvote.domain.repository.rating.IRatingFilterRepository
import com.petsvote.domain.usecases.rating.IGetRatingFilterUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRatingFilterUseCase @Inject constructor(
    private val ratingFilterRepository: IRatingFilterRepository
): IGetRatingFilterUseCase {

    override suspend fun getRatingFilter(): Flow<RatingFilter> {
        return ratingFilterRepository.getRatingFilter()
    }
}