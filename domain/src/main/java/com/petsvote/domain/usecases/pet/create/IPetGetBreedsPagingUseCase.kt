package com.petsvote.domain.usecases.pet.create

import androidx.paging.PagingData
import com.petsvote.core.adapter.Item
import kotlinx.coroutines.flow.Flow

interface IPetGetBreedsPagingUseCase {

    suspend fun getRating(text: String? = ""): Flow<PagingData<Item>>

}