package com.petsvote.domain.usecases.pet.impl

import com.petsvote.domain.repository.IPetRepository
import com.petsvote.domain.usecases.pet.ISetImagePetProfileUseCase
import javax.inject.Inject

class SetImagePetProfileUseCase@Inject constructor(
    private val petRepository: IPetRepository
): ISetImagePetProfileUseCase {

    override suspend fun setImagePet(byteArray: ByteArray) {
        petRepository.setImage(byteArray)
    }
}