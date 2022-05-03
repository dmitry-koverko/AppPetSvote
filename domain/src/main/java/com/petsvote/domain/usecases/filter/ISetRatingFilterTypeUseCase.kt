package com.petsvote.domain.usecases.filter

import com.petsvote.domain.entity.filter.RatingFilterType

interface ISetRatingFilterTypeUseCase {
    suspend fun setRatingFilterType(filterType: RatingFilterType)
}