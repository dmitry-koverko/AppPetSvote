package com.petsvote.domain.usecases.user

interface ICheckLocationUserUseCase {

    suspend fun checkLocationUser(): Boolean

}