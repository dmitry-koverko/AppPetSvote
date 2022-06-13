package com.petsvote.domain.usecases.user

import com.petsvote.domain.entity.user.UserInfo

interface IGetUserUseCase {
    suspend fun getUser(): UserInfo
}