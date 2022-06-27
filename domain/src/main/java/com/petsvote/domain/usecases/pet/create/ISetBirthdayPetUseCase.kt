package com.petsvote.domain.usecases.pet.create

interface ISetBirthdayPetUseCase {

    suspend fun setBirthdayPet(date: String)

}