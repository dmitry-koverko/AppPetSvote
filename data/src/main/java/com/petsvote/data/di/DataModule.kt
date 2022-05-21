package com.petsvote.data.di

import com.petsvote.data.repository.BreedRepository
import com.petsvote.data.repository.ConfigurationRepository
import com.petsvote.data.repository.RatingFilterRepository
import com.petsvote.data.repository.UserRepository
import com.petsvote.domain.repository.IBreedRepository
import com.petsvote.domain.repository.rating.RatingPagingRepository
import com.petsvote.domain.repository.IUserRepository
import com.petsvote.domain.repository.rating.IRatingFilterRepository
import com.petsvote.domain.repository.rating.RatingRepository
import com.petsvote.domain.usecases.configuration.GetLocaleLanguageCodeUseCase
import com.petsvote.retrofit.api.ConfigurationApi
import com.petsvote.retrofit.api.RatingApi
import com.petsvote.retrofit.api.UserApi
import com.petsvote.room.dao.BreedsDao
import com.petsvote.room.dao.RatingFilterDao
import com.petsvote.room.dao.UserDao
import dagger.Module
import dagger.Provides

@Module
class DataModule {


    @Provides
    fun provideBreedsRepository(
        breedsDao: BreedsDao,
        configurationApi: ConfigurationApi,
        localeLanguageCodeUseCase: GetLocaleLanguageCodeUseCase
    ): IBreedRepository{
        return BreedRepository(breedsDao, configurationApi, localeLanguageCodeUseCase)
    }

    @Provides
    fun provideRatingFilterRepository(
        ratingFilterDao: RatingFilterDao
    ): IRatingFilterRepository {
        return RatingFilterRepository(ratingFilterDao = ratingFilterDao)
    }

    @Provides
    fun provideRatingRemoteRepository(
        ratingApi: RatingApi,
        IUserRepository: IUserRepository,
        languageCodeUseCase: GetLocaleLanguageCodeUseCase,
        ratingFilterRepository: IRatingFilterRepository
    ): RatingRepository {
        return com.petsvote.data.repository.paging.RatingRepository(
            ratingApi = ratingApi,
            userRepository = IUserRepository,
            languageCodeUseCase = languageCodeUseCase,
            ratingFilterRepository = ratingFilterRepository
        )
    }

    @Provides
    fun provideRatingPagingRepository(
        ratingRepository: RatingRepository,
        ratingFilterRepository: IRatingFilterRepository,
        userRepository: IUserRepository
    ): RatingPagingRepository {
        return com.petsvote.data.repository.paging.RatingPagingRepository(
            ratingRepository = ratingRepository,
            ratingFilterRepository = ratingFilterRepository,
            userRepository = userRepository
        )
    }

    @Provides
    fun provideUserRemoteRepository(
        userApi: UserApi,
        userDao: UserDao,
        getLocaleLanguageCodeUseCase: GetLocaleLanguageCodeUseCase
    ): IUserRepository {
        return UserRepository(
            userApi = userApi,
            userDao = userDao,
            getLocaleLanguageCodeUseCase = getLocaleLanguageCodeUseCase
        )
    }

    @Provides
    fun provideConfigurationRemoteRepository(
        configurationApi: ConfigurationApi,
        getLocaleLanguageCodeUseCaseImpl: com.petsvote.domain.usecases.configuration.impl.GetLocaleLanguageCodeUseCaseImpl
    ): com.petsvote.domain.repository.IConfigurationRepository {
        return ConfigurationRepository(
            configurationApi = configurationApi,
            getLocaleLanguageCodeUseCase = getLocaleLanguageCodeUseCaseImpl
        )
    }

}
