package com.petsvote.domain.usecases.pet.create

interface ISetKindPetUseCase {

    suspend fun setKind(id: Int, title: String)

}