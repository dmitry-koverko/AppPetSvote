package com.petsvote.domain.usecases.pet.create.impl

import com.petsvote.domain.repository.IPetRepository
import com.petsvote.domain.usecases.pet.create.IGetBreedPetUseCase
import javax.inject.Inject

class GetBreedPetUseCase @Inject constructor(
    private val petRepository: IPetRepository
): IGetBreedPetUseCase {

    override suspend fun getBreed(): String {
        return petRepository.getSelectBreed()
    }
}