package com.petsvote.domain.usecases.user

import com.petsvote.domain.entity.user.UserInfo

interface SaveUserToLocalUseCase {
    suspend fun saveUserToLocal(user: UserInfo)
}