package com.petsvote.domain.usecases.pet.create.impl

import com.petsvote.domain.repository.IPetRepository
import com.petsvote.domain.usecases.pet.create.ISetSexPetUseCase
import javax.inject.Inject

class SetSexPetUseCase @Inject constructor(
    private val petRepository: IPetRepository
): ISetSexPetUseCase {
    override suspend fun setSexPet(sex: Int) {
        petRepository.setSelectSex(sex)
    }
}