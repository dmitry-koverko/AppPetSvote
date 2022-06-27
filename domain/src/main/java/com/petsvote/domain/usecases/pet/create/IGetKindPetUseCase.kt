package com.petsvote.domain.usecases.pet.create

import com.petsvote.domain.entity.filter.Kind

interface IGetKindPetUseCase {

    suspend fun getKind(): Kind?

}