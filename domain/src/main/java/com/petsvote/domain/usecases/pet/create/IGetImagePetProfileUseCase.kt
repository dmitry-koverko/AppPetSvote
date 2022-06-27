package com.petsvote.domain.usecases.pet.create

import kotlinx.coroutines.flow.Flow

interface IGetImagePetProfileUseCase {

    suspend fun getImagePetProfile(): Flow<ByteArray>

}