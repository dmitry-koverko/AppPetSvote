package com.petsvote.domain.usecases.pet.impl

import com.petsvote.domain.repository.IPetRepository
import com.petsvote.domain.usecases.pet.ISetEmptyPetProfileUseCase
import javax.inject.Inject

class SetEmptyPetProfileUseCase @Inject constructor(
    private val petRepository: IPetRepository
): ISetEmptyPetProfileUseCase {

    override suspend fun setEmptyPetProfile() {
        petRepository.insertEmptyPetProfile()
    }
}