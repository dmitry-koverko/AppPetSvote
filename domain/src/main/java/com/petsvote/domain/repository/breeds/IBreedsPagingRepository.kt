package com.petsvote.domain.repository.breeds

import androidx.paging.PagingData
import com.petsvote.core.adapter.Item
import kotlinx.coroutines.flow.Flow

interface IBreedsPagingRepository {
    fun getBreeds(text: String? = ""): Flow<PagingData<Item>>
}