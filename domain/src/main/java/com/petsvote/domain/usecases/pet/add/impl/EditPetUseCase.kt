package com.petsvote.domain.usecases.pet.add.impl

import android.graphics.Bitmap
import com.petsvote.domain.entity.pet.Pet
import com.petsvote.domain.entity.user.DataResponse
import com.petsvote.domain.repository.IPetRepository
import com.petsvote.domain.repository.IUserRepository
import com.petsvote.domain.usecases.filter.IGetKindsUseCase
import com.petsvote.domain.usecases.pet.add.IEditPetUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EditPetUseCase@Inject constructor(
    private val petRepository: IPetRepository,
    private val userRepository: IUserRepository,
    private val getKindsUseCase: IGetKindsUseCase
): IEditPetUseCase {
    override suspend fun editPet(list: List<Bitmap?>, kindId: Int): Flow<DataResponse<Pet>> = flow {
        emit(DataResponse.Loading)
        val kind: String = getKindsUseCase.getKinds(kindId).firstOrNull()?.name ?: "cat"
        val data = petRepository.editPet(list, kind, 1489274)
        var d = userRepository.getUser()
        if(data != null) {
            emit(DataResponse.Success<Pet>(data))
        }
        else emit(DataResponse.Error)
    }
}