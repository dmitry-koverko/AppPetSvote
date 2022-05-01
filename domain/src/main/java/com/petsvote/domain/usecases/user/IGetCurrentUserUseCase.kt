package com.petsvote.domain.usecases.user

import com.petsvote.domain.entity.user.DataResponse
import com.petsvote.domain.entity.user.RegisterUserParams
import com.petsvote.domain.entity.user.UserInfo
import kotlinx.coroutines.flow.Flow

interface IGetCurrentUserUseCase {
    suspend fun getCurrentUser(): UserInfo?
}