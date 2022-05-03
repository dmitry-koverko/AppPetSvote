package com.petsvote.data.repository

import com.petsvote.data.mappers.checkResult
import com.petsvote.data.mappers.toLocalUser
import com.petsvote.data.mappers.toUserInfoUC
import com.petsvote.data.mappers.toUserPets
import com.petsvote.domain.entity.user.DataResponse
import com.petsvote.domain.entity.user.RegisterUserParams
import com.petsvote.domain.entity.user.UserInfo
import com.petsvote.domain.entity.user.UserPet
import com.petsvote.domain.repository.IUserRepository
import com.petsvote.domain.usecases.configuration.GetLocaleLanguageCodeUseCase
import com.petsvote.retrofit.api.UserApi
import com.petsvote.retrofit.entity.user.Register
import com.petsvote.retrofit.entity.user.User
import com.petsvote.room.dao.UserDao
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userApi: UserApi,
    private val userDao: UserDao,
    private val getLocaleLanguageCodeUseCase: GetLocaleLanguageCodeUseCase
) : IUserRepository {

    override suspend fun registerUser(params: RegisterUserParams): UserInfo? {
        return checkResult<Register>(userApi.registerUser(code = params.code))?.toUserInfoUC()
    }

    override suspend fun getUser(): UserInfo? {
        val user = checkResult<User>(
            userApi.getCurrentUser(userDao.getToken(), getLocaleLanguageCodeUseCase.getLanguage())
        )
            ?.toUserInfoUC()
        if (user != null) {
            var token = userDao.getToken()
            userDao.update(user.toLocalUser(token))
        }
        return user
    }

    override suspend fun getCountUserPets(): Int {
        return userDao.getUser().pet.size
    }

    override suspend fun getUserPets(): Flow<List<UserPet>> =
        flow {
            run {
                userDao.getUserFlow().collect {
                    var listPets: List<UserPet> = it.pet.toUserPets()
                    emit(listPets)
                }
            }
        }

    override suspend fun getSimpleUserPets(): List<UserPet> {
        return userDao.getUser().pet.toUserPets()
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