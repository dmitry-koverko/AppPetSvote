package com.petsvote.domain.usecases.pet.impl

import com.petsvote.domain.entity.pet.PetPhoto
import com.petsvote.domain.repository.IPetRepository
import com.petsvote.domain.usecases.pet.IGetImagesCropUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImagesCropUseCase @Inject constructor(
    private val petRepository: IPetRepository
): IGetImagesCropUseCase {

    override suspend fun getImagesCrop(): Flow<List<PetPhoto>> {
        return petRepository.getImagesCrop()
    }
}