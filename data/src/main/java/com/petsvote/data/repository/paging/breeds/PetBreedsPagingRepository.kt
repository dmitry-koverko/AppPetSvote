package com.petsvote.data.repository.paging.breeds

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.petsvote.core.adapter.Item
import com.petsvote.domain.repository.IBreedRepository
import com.petsvote.domain.repository.breeds.IBreedsPagingRepository
import com.petsvote.domain.repository.breeds.IPetBreedsPagingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PetBreedsPagingRepository @Inject constructor(
    private val breedsRepository: IBreedRepository
) : IPetBreedsPagingRepository {

    override fun getBreeds(text: String?, type: String): Flow<PagingData<Item>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                PetBreedsPagingSource(
                    breedsRepository = breedsRepository,
                    text = text,
                    type = type
                )
            }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 50
    }
}