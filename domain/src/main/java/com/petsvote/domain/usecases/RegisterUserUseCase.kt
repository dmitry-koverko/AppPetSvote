package com.petsvote.domain.usecases

import com.petsvote.domain.entity.user.DataResponse
import com.petsvote.domain.entity.user.RegisterUserParams
import com.petsvote.domain.entity.user.UserInfo
import kotlinx.coroutines.flow.Flow

interface RegisterUserUseCase {
    suspend fun registerUser(params: RegisterUserParams): Flow<DataResponse<UserInfo>>
}