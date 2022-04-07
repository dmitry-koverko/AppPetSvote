package com.petsvote.data.repository

import com.petsvote.data.mappers.checkResult
import com.petsvote.data.mappers.toUserInfoUC
import com.petsvote.domain.entity.user.RegisterUserParams
import com.petsvote.domain.entity.user.UserInfo
import com.petsvote.domain.repository.UserRemoteRepository
import com.petsvote.retrofit.api.UserApi
import com.petsvote.retrofit.entity.user.Register
import javax.inject.Inject

class UserRemoteRepository @Inject constructor (private val userApi: UserApi): UserRemoteRepository {

    override suspend fun registerUser(params: RegisterUserParams): UserInfo? {
        return checkResult<Register>(userApi.registerUser(code = params.code))?.toUserInfoUC()
    }


}