package com.petsvote.domain.usecases.rating

import androidx.paging.PagingData
import com.petsvote.core.adapter.Item
import com.petsvote.domain.entity.pet.RatingPet
import kotlinx.coroutines.flow.Flow

interface GetRatingUseCase {
    suspend fun getRating(): Flow<PagingData<Item>>
    suspend fun getRating(offset: Int, count: Int): Flow<List<Item>>
    suspend fun getRatingByBreedId(breedId: Int): Flow<List<Item>>
    suspend fun getRatingTop(firstIndex: Int): Flow<List<Item>>
}