package com.petsvote.domain.usecases.filter.impl

import com.petsvote.domain.repository.rating.IRatingFilterRepository
import com.petsvote.domain.usecases.filter.ISetMaxAgeUseCase
import com.petsvote.domain.usecases.filter.ISetMinAgeUseCase
import javax.inject.Inject

class SetMinAgeUseCase @Inject constructor(
    private val ratingFilterRepository: IRatingFilterRepository
): ISetMinAgeUseCase {
    override suspend fun setMin(age: Int) {
        ratingFilterRepository.setMinAge(age)
    }
}