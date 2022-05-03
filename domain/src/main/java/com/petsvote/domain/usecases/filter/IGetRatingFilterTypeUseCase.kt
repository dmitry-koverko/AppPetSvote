package com.petsvote.domain.usecases.filter

import com.petsvote.domain.entity.filter.RatingFilterType

interface IGetRatingFilterTypeUseCase {
    suspend fun getRatingFilterType(): RatingFilterType
}