package com.petsvote.domain.usecases.pet

interface ISetImagePetProfileUseCase {

    suspend fun setImagePet(byteArray: ByteArray)

}