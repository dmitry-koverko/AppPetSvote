package com.petsvote.domain.usecases.pet.create.impl

import com.petsvote.core.adapter.Item
import com.petsvote.domain.entity.breed.Breed
import com.petsvote.domain.repository.IPetRepository
import com.petsvote.domain.usecases.pet.create.ISetPetBreedUseCase
import javax.inject.Inject

class SetPetBreedUseCase @Inject constructor(
    private val petRepository: IPetRepository
): ISetPetBreedUseCase {

    override suspend fun setBreed(item: Item) {
        petRepository.setSelectBreed((item as Breed).breedId, (item as Breed).breedName)
    }
}