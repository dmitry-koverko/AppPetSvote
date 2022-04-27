package com.petsvote.data.di

import com.petsvote.data.repository.ConfigurationRepository
import com.petsvote.domain.repository.rating.RatingPagingRepository
import com.petsvote.domain.repository.UserRepository
import com.petsvote.domain.repository.rating.RatingRepository
import com.petsvote.retrofit.api.ConfigurationApi
import com.petsvote.retrofit.api.RatingApi
import com.petsvote.retrofit.api.UserApi
import com.petsvote.room.dao.UserDao
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideRatingRemoteRepository(ratingApi: RatingApi, userRepository: UserRepository): RatingRepository {
        return com.petsvote.data.repository.paging.RatingRepository(
            ratingApi = ratingApi,
            userRepository = userRepository
        )
    }

    @Provides
    fun provideRatingPagingRepository(ratingRepository: RatingRepository): RatingPagingRepository {
        return com.petsvote.data.repository.paging.RatingPagingRepository(ratingRepository = ratingRepository)
    }

    @Provides
    fun provideUserRemoteRepository(userApi: UserApi, userDao: UserDao): UserRepository {
        return com.petsvote.data.repository.UserRepository(userApi = userApi, userDao = userDao)
    }

    @Provides
    fun provideConfigurationRemoteRepository(
        configurationApi: ConfigurationApi,
        getLocaleLanguageCodeUseCaseImpl: com.petsvote.domain.usecases.configuration.impl.GetLocaleLanguageCodeUseCaseImpl
    ): com.petsvote.domain.repository.ConfigurationRepository {
        return ConfigurationRepository(
            configurationApi = configurationApi,
            getLocaleLanguageCodeUseCase = getLocaleLanguageCodeUseCaseImpl
        )
    }

}
