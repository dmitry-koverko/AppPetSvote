package com.petsvote.domain.usecases.pet

import com.petsvote.domain.entity.pet.PetPhoto
import kotlinx.coroutines.flow.Flow

interface IGetImagesCropUseCase {

    suspend fun getImagesCrop(): Flow<List<PetPhoto>>

}