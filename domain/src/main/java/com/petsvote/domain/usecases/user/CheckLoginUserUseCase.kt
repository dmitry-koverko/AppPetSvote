package com.petsvote.domain.usecases.user

interface CheckLoginUserUseCase {
    suspend fun checkLoginUser(): Boolean
}