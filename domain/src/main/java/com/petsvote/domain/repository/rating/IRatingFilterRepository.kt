package com.petsvote.domain.repository.rating

import com.petsvote.domain.entity.filter.RatingFilter
import com.petsvote.domain.entity.filter.RatingFilterType
import kotlinx.coroutines.flow.Flow

interface IRatingFilterRepository {
    suspend fun getRatingFilter(): Flow<RatingFilter>
    suspend fun getSimpleRatingFilter(): RatingFilter
    suspend fun setBredIdRatingFilter(breedId: Int?)
    suspend fun setKindsRatingFilter(kinds: String?)
    suspend fun setDefaultRatingFilter()
    suspend fun setRatingFilterType(typeRating: RatingFilterType)
    suspend fun setSexRatingFilter(sex: String?)
}