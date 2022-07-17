package com.petsvote.domain.usecases.pet

import com.petsvote.domain.entity.pet.PetDetails

interface IGetPetDetailsUseCase {

    suspend fun getPetDetails(petId: Int): PetDetails?

}