package com.petsvote.domain.di

import com.petsvote.domain.repository.UserRemoteRepository
import com.petsvote.domain.usecases.RegisterUserUseCase
import com.petsvote.domain.usecases.impl.RegisterUserUseCaseImpl
import dagger.Module
import dagger.Provides
import javax.inject.Scope

@Module
class UseCaseModule {

    @Provides
    fun provideRegisterUserUseCase(userRemoteRepository: UserRemoteRepository): RegisterUserUseCase{
        return RegisterUserUseCaseImpl(userRemoteRepository)
    }

}
