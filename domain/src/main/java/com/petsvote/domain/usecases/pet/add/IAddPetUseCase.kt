package com.petsvote.domain.usecases.pet.add

import android.graphics.Bitmap
import com.petsvote.domain.entity.pet.Pet
import com.petsvote.domain.entity.user.DataResponse
import kotlinx.coroutines.flow.Flow

interface IAddPetUseCase {

    suspend fun addPet(list: List<Bitmap?>, kindId: Int): Flow<DataResponse<Pet>>

}