package com.petsvote.data.repository

import android.content.res.Configuration
import android.os.Build
import com.petsvote.data.mappers.checkResult
import com.petsvote.data.mappers.toLocalUser
import com.petsvote.data.mappers.toUserInfoUC
import com.petsvote.domain.entity.user.RegisterUserParams
import com.petsvote.domain.entity.user.UserInfo
import com.petsvote.domain.repository.UserRepository
import com.petsvote.domain.usecases.configuration.GetLocaleLanguageCodeUseCase
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

    override suspend fun checkLoginUser(): Boolean {
        val user = userDao.getUser()
        return user != null
    }

    override suspend fun saveUserToLocal(user: UserInfo) {
        userDao.insert(user.toLocalUser())
    }

    override suspend fun getToken(): String {
        return userDao.getToken()
    }


}