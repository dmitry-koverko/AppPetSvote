package com.petsvote.domain.usecases.pet.create

interface ISetSexPetUseCase {

    suspend fun setSexPet(sex: Int)

}