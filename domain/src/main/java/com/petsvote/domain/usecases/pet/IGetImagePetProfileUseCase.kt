package com.petsvote.domain.usecases.pet

import kotlinx.coroutines.flow.Flow

interface IGetImagePetProfileUseCase {

    suspend fun getImagePetProfile(): Flow<ByteArray>

}