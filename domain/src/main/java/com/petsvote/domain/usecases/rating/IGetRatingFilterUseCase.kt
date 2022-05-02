package com.petsvote.domain.usecases.rating

import com.petsvote.domain.entity.filter.RatingFilter
import kotlinx.coroutines.flow.Flow

interface IGetRatingFilterUseCase {
    suspend fun getRatingFilter(): Flow<RatingFilter>
}