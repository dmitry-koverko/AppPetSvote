package com.petsvote.domain.usecases.filter.impl

import androidx.paging.PagingData
import com.petsvote.core.adapter.Item
import com.petsvote.domain.repository.breeds.IBreedsPagingRepository
import com.petsvote.domain.usecases.filter.IGetBreedsPagingUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBreedsPagingUseCase @Inject constructor(
    private val breedsPagingRepository: IBreedsPagingRepository
): IGetBreedsPagingUseCase {

    override suspend fun getRating(text: String?): Flow<PagingData<Item>> {
       return breedsPagingRepository.getBreeds(text)
    }
}