package com.petsvote.domain.usecases.pet.create.impl

import com.petsvote.domain.repository.IPetRepository
import com.petsvote.domain.usecases.pet.create.ISetPetNameUseCase
import javax.inject.Inject

class SetPetNameUseCase @Inject constructor(
    private val petRepository: IPetRepository
): ISetPetNameUseCase {

    override suspend fun setPetName(name: String) {
        petRepository.setPetName(name)
    }
}