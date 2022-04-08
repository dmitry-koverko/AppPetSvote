package com.petsvote.data.di

import com.petsvote.domain.repository.UserRepository
import com.petsvote.retrofit.api.UserApi
import com.petsvote.room.dao.UserDao
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideUserRemoteRepository(userApi: UserApi, userDao: UserDao): UserRepository{
        return com.petsvote.data.repository.UserRepository(userApi = userApi, userDao = userDao)
    }

}
