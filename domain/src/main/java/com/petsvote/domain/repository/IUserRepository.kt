package com.petsvote.domain.repository

import com.petsvote.domain.entity.user.DataResponse
import com.petsvote.domain.entity.user.RegisterUserParams
import com.petsvote.domain.entity.user.UserInfo
import com.petsvote.domain.entity.user.UserPet
import kotlinx.coroutines.flow.Flow

interface IUserRepository {

    suspend fun registerUser(params: RegisterUserParams): UserInfo?
    suspend fun getUser(): UserInfo?
    suspend fun getCurrentUser(): UserInfo
    suspend fun getCountUserPets(): Int
    suspend fun getUserPets(): Flow<List<UserPet>>
    suspend fun getSimpleUserPets(): List<UserPet>
    suspend fun checkLoginUser(): Boolean
    suspend fun saveUserToLocal(user: UserInfo)
    suspend fun getToken(): String
    suspend fun setImage(bytes: ByteArray)
    suspend fun getImage(): ByteArray

}