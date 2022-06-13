package com.petsvote.domain.di

import com.petsvote.domain.repository.IUserRepository
import com.petsvote.domain.usecases.user.*
import com.petsvote.domain.usecases.user.impl.*
import dagger.Module
import dagger.Provides

@Module
class UserUseCaseModule {

    @Provides
    fun provideUserUseCase(userRepository: IUserRepository): IGetUserUseCase {
        return GetUserUseCase(userRepository)
    }

    @Provides
    fun provideCheckUserLocationUseCase(userRepository: IUserRepository): ICheckLocationUserUseCase {
        return CheckLocationUserUseCase(userRepository)
    }

    @Provides
    fun provideGetUserPetsUseCase(userRepository: IUserRepository): IGetUserPetsUseCase {
        return GetUserPetsUseCase(userRepository)
    }

    @Provides
    fun provideGetCurrentUserUseCase(userRepository: IUserRepository): IGetCurrentUserUseCase {
        return GetCurrentUserUseCase(userRepository)
    }

    @Provides
    fun provideRegisterUserUseCase(userRepository: IUserRepository): IRegisterUserUseCase {
        return RegisterUserUseCase(userRepository)
    }

    @Provides
    fun provideCheckLoginUserUseCase(userRepository: IUserRepository): com.petsvote.domain.usecases.user.ICheckLoginUserUseCase {
        return CheckLoginUserUseCase(userRepository)
    }

    @Provides
    fun provideSaveLocalUserUseCase(userRepository: IUserRepository): ISaveUserToLocalUseCase {
        return SaveUserToLocalUseCase(userRepository)
    }


}
