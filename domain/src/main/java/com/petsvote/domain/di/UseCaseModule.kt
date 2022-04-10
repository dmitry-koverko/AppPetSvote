package com.petsvote.domain.di

import com.petsvote.domain.repository.UserRepository
import com.petsvote.domain.usecases.user.CheckLoginUserUseCase
import com.petsvote.domain.usecases.user.RegisterUserUseCase
import com.petsvote.domain.usecases.user.impl.CheckLoginUserUseCaseImpl
import com.petsvote.domain.usecases.user.impl.RegisterUserUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun provideRegisterUserUseCase(userRepository: UserRepository): RegisterUserUseCase {
        return RegisterUserUseCaseImpl(userRepository)
    }

    @Provides
    fun provideCheckLoginUserUseCase(userRepository: UserRepository): CheckLoginUserUseCase {
        return CheckLoginUserUseCaseImpl(userRepository)
    }

}
