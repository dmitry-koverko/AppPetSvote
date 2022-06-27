package com.petsvote.domain.usecases.pet.create

interface IRemovePetImageUseCase {

    suspend fun removeImage(id: Int)

}