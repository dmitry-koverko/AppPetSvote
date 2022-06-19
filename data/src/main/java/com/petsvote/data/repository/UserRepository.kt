package com.petsvote.data.repository

import com.petsvote.data.mappers.*
import com.petsvote.domain.entity.configuration.UserProfile
import com.petsvote.domain.entity.user.RegisterUserParams
import com.petsvote.domain.entity.user.UserInfo
import com.petsvote.domain.entity.user.UserPet
import com.petsvote.domain.repository.IUserRepository
import com.petsvote.domain.usecases.configuration.GetLocaleLanguageCodeUseCase
import com.petsvote.retrofit.api.UserApi
import com.petsvote.retrofit.entity.user.Register
import com.petsvote.retrofit.entity.user.User
import com.petsvote.room.dao.UserProfileDao
import com.petsvote.room.dao.UserDao
import com.petsvote.room.entity.EntityUserProfile
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userApi: UserApi,
    private val userDao: UserDao,
    private val imagesDao: UserProfileDao,
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

    override suspend fun getCurrentUser(): UserInfo {
        return userDao.getUser().toUserInfo()
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

    override suspend fun setImage(bytes: ByteArray) {
        imagesDao.updateImage(bytes)
    }

    override suspend fun getImage(): Flow<UserProfile> = flow {
        run {
            imagesDao.getImageFlow().collect {
                it?.toUserProfile()?.let { it1 -> emit(it1) }
            }
        }

    }

    override suspend fun setImageCrop(bytes: ByteArray) {
        imagesDao.updateImageCrop(bytes)
    }


}