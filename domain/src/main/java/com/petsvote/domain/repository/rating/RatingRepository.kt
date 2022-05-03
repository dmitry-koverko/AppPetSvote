package com.petsvote.domain.repository.rating

import androidx.paging.PagingData
import com.petsvote.core.adapter.Item
import com.petsvote.domain.entity.pet.RatingPet
import kotlinx.coroutines.flow.Flow

interface RatingRepository {
    suspend fun getRating(offset: Int, limit: Int?,breedId: Int?): List<RatingPet>
}