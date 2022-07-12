package com.petsvote.domain.usecases.filter.impl

import com.petsvote.domain.repository.rating.IRatingFilterRepository
import com.petsvote.domain.usecases.filter.ISetInsertDefaultRatingFilterUseCase
import javax.inject.Inject

class SetInsertDefaultRatingFilterUseCase @Inject constructor(
    private val ratingFilterRepository: IRatingFilterRepository
): ISetInsertDefaultRatingFilterUseCase {
    override suspend fun insert() {
        ratingFilterRepository.insertDefault()
    }
}