package com.petsvote.domain.usecases.configuration

interface ISetImageCropUseCase {
    suspend fun setImageCrop(bytes: ByteArray)
}