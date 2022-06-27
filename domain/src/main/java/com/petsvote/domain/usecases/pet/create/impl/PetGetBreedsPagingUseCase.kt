package com.petsvote.domain.usecases.pet.create.impl

import androidx.paging.PagingData
import com.petsvote.core.adapter.Item
import com.petsvote.domain.repository.IPetRepository
import com.petsvote.domain.repository.breeds.IBreedsPagingRepository
import com.petsvote.domain.repository.breeds.IPetBreedsPagingRepository
import com.petsvote.domain.usecases.filter.impl.GetKindsUseCase
import com.petsvote.domain.usecases.pet.create.IPetGetBreedsPagingUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PetGetBreedsPagingUseCase @Inject constructor(
    private val petRepository: IPetRepository,
    private val breedsPagingRepository: IPetBreedsPagingRepository,
    private val getKindsUseCase: GetKindsUseCase
): IPetGetBreedsPagingUseCase {

    override suspend fun getRating(text: String?): Flow<PagingData<Item>> {

        val idKind = petRepository.getSelectKindId()
        var type = getKindsUseCase.getKinds(0).filter { it.id == idKind }.firstOrNull()?.name ?: ""
        return breedsPagingRepository.getBreeds(text, type)
    }
}