package com.petsvote.domain.usecases.pet

interface IRemovePetImageUseCase {

    suspend fun removeImage(id: Int)

}