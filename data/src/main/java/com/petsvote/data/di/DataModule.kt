package com.petsvote.data.di

import com.petsvote.domain.repository.UserRepository
import com.petsvote.retrofit.api.UserApi
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideUserRemoteRepository(userApi: UserApi): UserRepository{
        return com.petsvote.data.repository.UserRepository(userApi = userApi)
    }

}
