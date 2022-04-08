package com.petsvote.data.repository

import com.petsvote.data.mappers.checkResult
import com.petsvote.data.mappers.toUserInfoUC
import com.petsvote.domain.entity.user.RegisterUserParams
import com.petsvote.domain.entity.user.UserInfo
import com.petsvote.domain.repository.UserRepository
import com.petsvote.retrofit.api.UserApi
import com.petsvote.retrofit.entity.user.Register
import com.petsvote.room.dao.UserDao
import javax.inject.Inject

class UserRepository @Inject constructor (
    private val userApi: UserApi,
    private val userDao: UserDao): UserRepository {

    override suspend fun registerUser(params: RegisterUserParams): UserInfo? {
        return checkResult<Register>(userApi.registerUser(code = params.code))?.toUserInfoUC()
    }



}