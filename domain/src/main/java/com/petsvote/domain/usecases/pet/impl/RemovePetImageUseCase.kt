package com.petsvote.domain.usecases.pet.impl

import com.petsvote.domain.repository.IPetRepository
import com.petsvote.domain.usecases.pet.IRemovePetImageUseCase
import javax.inject.Inject

class RemovePetImageUseCase @Inject constructor(
    private val petRepository: IPetRepository
): IRemovePetImageUseCase {
    override suspend fun removeImage(id: Int) {
        petRepository.removeImagePet(id)
    }
}