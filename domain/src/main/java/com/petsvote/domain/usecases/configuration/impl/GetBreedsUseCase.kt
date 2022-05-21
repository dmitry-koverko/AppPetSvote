package com.petsvote.domain.usecases.configuration.impl

import com.petsvote.domain.repository.IBreedRepository
import com.petsvote.domain.usecases.configuration.IGetBreedsUseCase

class GetBreedsUseCase(
    private val breedsRepository: IBreedRepository
): IGetBreedsUseCase {
    override suspend fun updateBreeds() {
        breedsRepository.updateBreeds()
    }
}