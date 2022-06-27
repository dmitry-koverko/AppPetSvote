package com.petsvote.domain.usecases.pet.create

interface IAddImageCropUseCase {

    suspend fun adImage(byteArray: ByteArray)

}