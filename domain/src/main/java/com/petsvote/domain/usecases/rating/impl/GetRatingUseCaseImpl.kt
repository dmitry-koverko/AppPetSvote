package com.petsvote.domain.usecases.rating.impl

import androidx.paging.PagingData
import com.petsvote.core.adapter.Item
import com.petsvote.domain.repository.rating.RatingPagingRepository
import com.petsvote.domain.repository.rating.RatingRepository
import com.petsvote.domain.usecases.rating.GetRatingUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRatingUseCaseImpl @Inject constructor(
    private val ratingPagingRepository: RatingPagingRepository,
    private val ratingRepository: RatingRepository
): GetRatingUseCase {

    override suspend fun getRating(): Flow<PagingData<Item>> {
        return ratingPagingRepository.getRating()
    }

    override suspend fun getRating(offset: Int, count: Int): Flow<List<Item>> = flow {
        emit(ratingRepository.getRating(offset, count, null, ""))
    }

    override suspend fun getRatingByBreedId(breedId: Int): Flow<List<Item>> = flow {
        emit(ratingRepository.getRatingUserPet(breedId))
    }

    override suspend fun getRatingTop(firstIndex: Int): Flow<List<Item>> = flow {
        var limit = 50;
        var offset = 0;
        if(50 - firstIndex > 0){
            offset = firstIndex - 50
            limit = 0
        }else {
            offset = firstIndex - 50
        }
        emit(ratingRepository.getRating(offset, limit, null, ""))
    }
}