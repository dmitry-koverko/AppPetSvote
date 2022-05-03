package com.petsvote.domain.repository.rating

import com.petsvote.domain.entity.filter.RatingFilter
import kotlinx.coroutines.flow.Flow

interface IRatingFilterRepository {
    suspend fun getRatingFilter(): Flow<RatingFilter>
    suspend fun getSimpleRatingFilter(): RatingFilter
    suspend fun setBredIdRatingFilter(breedId: Int?)
    suspend fun setDefaultRatingFilter()
}