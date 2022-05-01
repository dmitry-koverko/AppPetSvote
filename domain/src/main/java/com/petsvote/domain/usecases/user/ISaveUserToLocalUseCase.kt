package com.petsvote.domain.usecases.user

import com.petsvote.domain.entity.user.UserInfo

interface ISaveUserToLocalUseCase {
    suspend fun saveUserToLocal(user: UserInfo)
}