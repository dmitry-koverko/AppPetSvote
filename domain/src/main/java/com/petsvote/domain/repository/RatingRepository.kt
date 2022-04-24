package com.petsvote.domain.repository

import androidx.paging.PagingData
import com.petsvote.core.adapter.Item
import com.petsvote.domain.entity.pet.RatingPet
import kotlinx.coroutines.flow.Flow

interface RatingRepository {

    fun getRating(): Flow<PagingData<Item>>

}