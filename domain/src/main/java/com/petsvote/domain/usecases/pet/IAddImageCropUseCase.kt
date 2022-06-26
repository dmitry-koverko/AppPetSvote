package com.petsvote.domain.usecases.pet

interface IAddImageCropUseCase {

    suspend fun adImage(byteArray: ByteArray)

}