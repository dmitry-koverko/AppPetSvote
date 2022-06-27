package com.petsvote.domain.usecases.pet.create.impl

import com.petsvote.domain.entity.filter.Kind
import com.petsvote.domain.repository.IPetRepository
import com.petsvote.domain.usecases.filter.impl.GetKindsUseCase
import com.petsvote.domain.usecases.pet.create.IGetKindPetUseCase
import javax.inject.Inject

class GetKindPetUseCase @Inject constructor(
    private val petRepository: IPetRepository,
    private val getKindsUseCase: GetKindsUseCase
): IGetKindPetUseCase {
    override suspend fun getKind(): Kind? {
        val idKind = petRepository.getSelectKindId()
        return getKindsUseCase.getKinds(0).filter { it.id == idKind }.firstOrNull()
    }

}