package com.petsvote.domain.repository.rating

import androidx.paging.PagingData
import com.petsvote.core.adapter.Item
import com.petsvote.domain.entity.pet.RatingPet
import kotlinx.coroutines.flow.Flow

interface RatingPagingRepository {

    fun getRating(): Flow<PagingData<Item>>

}