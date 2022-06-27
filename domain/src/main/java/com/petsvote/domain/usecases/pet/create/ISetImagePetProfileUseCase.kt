package com.petsvote.domain.usecases.pet.create

interface ISetImagePetProfileUseCase {

    suspend fun setImagePet(byteArray: ByteArray)

}