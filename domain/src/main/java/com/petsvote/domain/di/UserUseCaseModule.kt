package com.petsvote.domain.di

import com.petsvote.domain.repository.UserRepository
import com.petsvote.domain.usecases.user.CheckLoginUserUseCase
import com.petsvote.domain.usecases.user.RegisterUserUseCase
import com.petsvote.domain.usecases.user.SaveUserToLocalUseCase
import com.petsvote.domain.usecases.user.impl.CheckLoginUserUseCaseImpl
import com.petsvote.domain.usecases.user.impl.RegisterUserUseCaseImpl
import com.petsvote.domain.usecases.user.impl.SaveUserToLocalUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class UserUseCaseModule {

    @Provides
    fun provideRegisterUserUseCase(userRepository: UserRepository): RegisterUserUseCase {
        return RegisterUserUseCaseImpl(userRepository)
    }

    @Provides
    fun provideCheckLoginUserUseCase(userRepository: UserRepository): CheckLoginUserUseCase {
        return CheckLoginUserUseCaseImpl(userRepository)
    }

    @Provides
    fun provideSaveLocalUserUseCase(userRepository: UserRepository): SaveUserToLocalUseCase {
        return SaveUserToLocalUseCaseImpl(userRepository)
    }


}
