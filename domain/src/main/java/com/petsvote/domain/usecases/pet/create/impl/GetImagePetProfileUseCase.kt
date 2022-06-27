package com.petsvote.domain.usecases.pet.create.impl

import com.petsvote.domain.repository.IPetRepository
import com.petsvote.domain.usecases.pet.create.IGetImagePetProfileUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImagePetProfileUseCase @Inject constructor(
    private val petRepository: IPetRepository
): IGetImagePetProfileUseCase {
    override suspend fun getImagePetProfile(): Flow<ByteArray> {
        return petRepository.getImage()
    }
}