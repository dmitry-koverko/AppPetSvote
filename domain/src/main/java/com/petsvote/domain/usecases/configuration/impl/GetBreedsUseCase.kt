package com.petsvote.domain.usecases.configuration.impl

import com.petsvote.domain.entity.breed.Breed
import com.petsvote.domain.repository.IBreedRepository
import com.petsvote.domain.repository.rating.IRatingFilterRepository
import com.petsvote.domain.usecases.configuration.IGetBreedsUseCase

class GetBreedsUseCase(
    private val breedsRepository: IBreedRepository
): IGetBreedsUseCase {

    override suspend fun updateBreeds() {
        breedsRepository.updateBreeds()
    }

    override suspend fun getBreeds(): List<Breed> {
        return breedsRepository.getBreeds(0, "")
    }
}