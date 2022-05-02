package com.petsvote.domain.repository.rating

import com.petsvote.domain.entity.filter.RatingFilter
import kotlinx.coroutines.flow.Flow

interface IRatingFilterRepository {
    suspend fun getRatingFilter(): Flow<RatingFilter>
}