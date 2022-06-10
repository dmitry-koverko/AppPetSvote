package com.petsvote.domain.usecases.filter.impl

import com.petsvote.domain.repository.rating.IRatingFilterRepository
import com.petsvote.domain.usecases.filter.ISetMaxAgeUseCase
import com.petsvote.domain.usecases.filter.ISetSexUseCase
import javax.inject.Inject

class SetMaxAgeUseCase @Inject constructor(
    private val ratingFilterRepository: IRatingFilterRepository
): ISetMaxAgeUseCase {
    override suspend fun setMax(age: Int) {
        ratingFilterRepository.setMaxAge(age)
    }
}