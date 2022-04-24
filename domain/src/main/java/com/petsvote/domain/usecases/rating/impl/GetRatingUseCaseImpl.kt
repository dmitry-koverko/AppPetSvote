package com.petsvote.domain.usecases.rating.impl

import androidx.paging.PagingData
import com.petsvote.core.adapter.Item
import com.petsvote.domain.entity.pet.RatingPet
import com.petsvote.domain.repository.RatingRepository
import com.petsvote.domain.usecases.rating.GetRatingUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRatingUseCaseImpl @Inject constructor(
    private val ratingRepository: RatingRepository
): GetRatingUseCase {

    override suspend fun getRating(): Flow<PagingData<Item>> {
        return ratingRepository.getRating()
    }
}