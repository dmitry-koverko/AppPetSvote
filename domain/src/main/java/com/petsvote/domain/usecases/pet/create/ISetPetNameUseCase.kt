package com.petsvote.domain.usecases.pet.create

interface ISetPetNameUseCase {

    suspend fun setPetName(name: String)

}