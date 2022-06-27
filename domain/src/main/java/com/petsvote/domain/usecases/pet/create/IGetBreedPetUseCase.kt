package com.petsvote.domain.usecases.pet.create

interface IGetBreedPetUseCase {

    suspend fun getBreed(): String

}