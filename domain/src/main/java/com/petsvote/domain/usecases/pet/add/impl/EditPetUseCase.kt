package com.petsvote.domain.usecases.pet.add.impl

import android.graphics.Bitmap
import com.petsvote.domain.entity.pet.Pet
import com.petsvote.domain.entity.user.DataResponse
import com.petsvote.domain.repository.IPetRepository
import com.petsvote.domain.usecases.pet.add.IEditPetUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EditPetUseCase@Inject constructor(
    private val petRepository: IPetRepository
): IEditPetUseCase {
    override suspend fun editPet(list: List<Bitmap?>): Flow<DataResponse<Pet>> = flow {
        emit(DataResponse.Loading)
        val data = petRepository.addPet(list, "cat")
        if(data != null) emit(DataResponse.Success<Pet>(data))
        else emit(DataResponse.Error)
    }
}