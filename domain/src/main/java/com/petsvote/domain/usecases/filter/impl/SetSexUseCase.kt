package com.petsvote.domain.usecases.filter.impl

import com.petsvote.domain.repository.rating.IRatingFilterRepository
import com.petsvote.domain.usecases.filter.ISetSexUseCase
import javax.inject.Inject

class SetSexUseCase @Inject constructor(
    private val ratingFilterRepository: IRatingFilterRepository
): ISetSexUseCase {
    override suspend fun setSex(sex: Int) {
        ratingFilterRepository.setSexRatingFilter(
            when(sex){
                0 -> null
                1 -> "MALE"
                2 -> "FEMALE"
                else -> null
            }
        )
    }
}