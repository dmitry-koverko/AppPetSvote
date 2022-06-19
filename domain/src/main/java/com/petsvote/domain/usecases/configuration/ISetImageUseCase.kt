package com.petsvote.domain.usecases.configuration

interface ISetImageUseCase {

    suspend fun setImage(bytes: ByteArray)

}