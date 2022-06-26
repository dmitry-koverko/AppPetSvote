package com.petsvote.domain.usecases.pet.impl

import com.petsvote.domain.repository.IPetRepository
import com.petsvote.domain.usecases.pet.IAddImageCropUseCase
import javax.inject.Inject

class AddImageCropUseCase @Inject constructor(
    private val petRepository: IPetRepository
): IAddImageCropUseCase {

    override suspend fun adImage(byteArray: ByteArray) {
        petRepository.addPetPhoto(byteArray)
    }
}