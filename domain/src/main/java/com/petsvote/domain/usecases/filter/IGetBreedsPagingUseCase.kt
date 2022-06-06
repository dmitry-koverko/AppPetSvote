package com.petsvote.domain.usecases.filter

import androidx.paging.PagingData
import com.petsvote.core.adapter.Item
import kotlinx.coroutines.flow.Flow

interface IGetBreedsPagingUseCase {
    suspend fun getRating(text: String? = ""): Flow<PagingData<Item>>
}