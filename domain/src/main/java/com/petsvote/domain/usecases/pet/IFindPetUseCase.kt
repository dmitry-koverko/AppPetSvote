package com.petsvote.domain.usecases.pet

import com.petsvote.domain.entity.pet.FindPet
import com.petsvote.domain.entity.user.DataResponse
import kotlinx.coroutines.flow.Flow

interface IFindPetUseCase {

    suspend fun findPet(petId: Int): Flow<DataResponse<FindPet>>

}