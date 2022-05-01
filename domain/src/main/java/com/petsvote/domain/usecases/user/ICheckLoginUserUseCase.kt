package com.petsvote.domain.usecases.user

interface ICheckLoginUserUseCase {
    suspend fun checkLoginUser(): Boolean
}