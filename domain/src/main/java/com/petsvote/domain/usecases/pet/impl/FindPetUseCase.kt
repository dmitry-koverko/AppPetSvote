package com.petsvote.domain.usecases.pet.impl

import com.petsvote.domain.entity.pet.FindPet
import com.petsvote.domain.entity.user.DataResponse
import com.petsvote.domain.entity.user.UserInfo
import com.petsvote.domain.repository.IPetRepository
import com.petsvote.domain.usecases.pet.IFindPetUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FindPetUseCase @Inject constructor(
    private val petRepository: IPetRepository
): IFindPetUseCase {
    override suspend fun findPet(petId: Int): Flow<DataResponse<FindPet>> = flow {
        emit(DataResponse.Loading)
        val data = petRepository.findPet(petId)
        if(data != null) emit(DataResponse.Success<FindPet>(data))
        else emit(DataResponse.Error)
    }
}