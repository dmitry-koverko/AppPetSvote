package com.petsvote.domain.usecases.pet.create

interface IGetInstagramUserNameUseCase {

    suspend fun getUsername(id: Long): String

}