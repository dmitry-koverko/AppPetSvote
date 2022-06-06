package com.petsvote.data.repository.paging.breeds

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.petsvote.core.adapter.Item
import com.petsvote.domain.repository.IBreedRepository
import com.petsvote.domain.repository.breeds.IBreedsPagingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BreedsPagingRepository @Inject constructor(
    private val breedsRepository: IBreedRepository
) : IBreedsPagingRepository {

    override fun getBreeds(text: String?): Flow<PagingData<Item>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                BreedsPagingSource(
                    breedsRepository = breedsRepository,
                    text = text
                )
            }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 50
    }
}