package com.petsvote.domain.usecases.pet.impl

import com.petsvote.domain.entity.pet.PetDetails
import com.petsvote.domain.repository.IPetRepository
import com.petsvote.domain.usecases.pet.IGetPetDetailsUseCase
import javax.inject.Inject

class GetPetDetailsUseCase @Inject constructor(
    private val petRepository: IPetRepository
): IGetPetDetailsUseCase {
    override suspend fun getPetDetails(petId: Int, userId: Int): PetDetails? {
        return petRepository.petDetails(petId, userId)
    }
}