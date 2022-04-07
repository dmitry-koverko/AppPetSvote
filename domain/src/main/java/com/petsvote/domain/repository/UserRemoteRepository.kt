package com.petsvote.domain.repository

import com.petsvote.domain.entity.user.DataResponse
import com.petsvote.domain.entity.user.RegisterUserParams
import com.petsvote.domain.entity.user.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserRemoteRepository {

    suspend fun registerUser(params: RegisterUserParams): UserInfo?

}