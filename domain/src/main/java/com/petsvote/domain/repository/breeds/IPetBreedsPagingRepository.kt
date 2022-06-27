package com.petsvote.domain.repository.breeds

import androidx.paging.PagingData
import com.petsvote.core.adapter.Item
import kotlinx.coroutines.flow.Flow

interface IPetBreedsPagingRepository {

    fun getBreeds(text: String? = "", type: String): Flow<PagingData<Item>>

}