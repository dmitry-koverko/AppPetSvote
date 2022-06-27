package com.petsvote.domain.usecases.pet.create

import com.petsvote.core.adapter.Item

interface ISetPetBreedUseCase {

    suspend fun setBreed(item: Item)

}