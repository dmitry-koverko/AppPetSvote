package com.petsvote.domain.usecases.pet.create.impl

import com.petsvote.domain.repository.IPetRepository
import com.petsvote.domain.usecases.pet.create.ISetKindPetUseCase
import javax.inject.Inject

class SetKindPetUseCase @Inject constructor(
    private val petRepository: IPetRepository
): ISetKindPetUseCase {
    override suspend fun setKind(id: Int, title: String) {
        petRepository.setSelectKind(id  ,title)
    }
}