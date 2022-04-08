package com.petsvote.domain.di

import com.petsvote.domain.repository.UserRepository
import com.petsvote.domain.usecases.RegisterUserUseCase
import com.petsvote.domain.usecases.impl.RegisterUserUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun provideRegisterUserUseCase(userRemoteRepository: UserRepository): RegisterUserUseCase{
        return RegisterUserUseCaseImpl(userRemoteRepository)
    }

}
