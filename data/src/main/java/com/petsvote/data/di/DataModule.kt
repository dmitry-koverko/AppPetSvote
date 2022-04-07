package com.petsvote.data.di

import com.petsvote.domain.repository.UserRemoteRepository
import com.petsvote.retrofit.api.UserApi
import dagger.Module
import dagger.Provides
import javax.inject.Scope

@Module
class DataModule {

    @Provides
    fun provideUserRemoteRepository(userApi: UserApi): UserRemoteRepository{
        return com.petsvote.data.repository.UserRemoteRepository(userApi = userApi)
    }

}
