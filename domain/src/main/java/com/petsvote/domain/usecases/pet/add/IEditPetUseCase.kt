package com.petsvote.domain.usecases.pet.add

import android.graphics.Bitmap
import com.petsvote.domain.entity.pet.Pet
import com.petsvote.domain.entity.user.DataResponse
import kotlinx.coroutines.flow.Flow

interface IEditPetUseCase {
    suspend fun editPet(list: List<Bitmap?>, kindId: Int): Flow<DataResponse<Pet>>
}