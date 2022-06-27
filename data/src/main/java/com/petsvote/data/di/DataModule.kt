package com.petsvote.data.di

import android.app.Application
import android.content.res.Resources
import com.petsvote.data.repository.*
import com.petsvote.data.repository.paging.breeds.BreedsPagingRepository
import com.petsvote.data.repository.paging.breeds.PetBreedsPagingRepository
import com.petsvote.domain.repository.*
import com.petsvote.domain.repository.rating.RatingPagingRepository
import com.petsvote.domain.repository.breeds.IBreedsPagingRepository
import com.petsvote.domain.repository.breeds.IPetBreedsPagingRepository
import com.petsvote.domain.repository.rating.IRatingFilterRepository
import com.petsvote.domain.repository.rating.RatingRepository
import com.petsvote.domain.usecases.configuration.GetLocaleLanguageCodeUseCase
import com.petsvote.domain.usecases.pet.create.IPetGetBreedsPagingUseCase
import com.petsvote.domain.usecases.pet.create.impl.PetGetBreedsPagingUseCase
import com.petsvote.retrofit.api.ApiInstagram
import com.petsvote.retrofit.api.ConfigurationApi
import com.petsvote.retrofit.api.RatingApi
import com.petsvote.retrofit.api.UserApi
import com.petsvote.room.dao.*
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun providePetRepository(
        userDao: UserDao,
        petProfileDao: PetProfileDao,
        localeLanguageCodeUseCase: GetLocaleLanguageCodeUseCase
    ): IPetRepository {
        return PetRepository( userDao = userDao, profilePetDao = petProfileDao, getLocaleLanguageCodeUseCase = localeLanguageCodeUseCase)
    }

    @Provides
    fun providePreferencesRepository(
        context: Application
    ): IPreferencesRepository {
        return PreferencesRepository( context = context)
    }

    @Provides
    fun provideBreedsPagingRepository(
        breedsRepository: IBreedRepository
    ): IBreedsPagingRepository {
        return BreedsPagingRepository(
            breedsRepository = breedsRepository
        )
    }

    @Provides
    fun providePetGetBreedsPagingUseCase(
        breedsRepository: IBreedRepository
    ): IPetBreedsPagingRepository {
        return PetBreedsPagingRepository(
            breedsRepository = breedsRepository
        )
    }

    @Provides
    fun provideResourcesRepository(
        resources: Resources,
        context: Application
    ): IResourcesRepository {
        return ResourcesRepository(resources = resources, context = context)
    }

    @Provides
    fun provideBreedsRepository(
        breedsDao: BreedsDao,
        petProfileDao: PetProfileDao,
        configurationApi: ConfigurationApi,
        localeLanguageCodeUseCase: GetLocaleLanguageCodeUseCase,
        filterRepository: IRatingFilterRepository
    ): IBreedRepository {
        return BreedRepository(
            petProfileDao = petProfileDao,
            breedsDao = breedsDao,
            configurationApi = configurationApi,
            localeLanguageCodeUseCase = localeLanguageCodeUseCase,
            filterRepository = filterRepository
        )
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
        ratingFilterRepository: IRatingFilterRepository,
        breedsDao: BreedsDao,
    ): RatingRepository {
        return com.petsvote.data.repository.paging.rating.RatingRepository(
            ratingApi = ratingApi,
            userRepository = IUserRepository,
            languageCodeUseCase = languageCodeUseCase,
            ratingFilterRepository = ratingFilterRepository,
            breedsDao = breedsDao
        )
    }

    @Provides
    fun provideRatingPagingRepository(
        ratingRepository: RatingRepository,
        ratingFilterRepository: IRatingFilterRepository,
        userRepository: IUserRepository
    ): RatingPagingRepository {
        return com.petsvote.data.repository.paging.rating.RatingPagingRepository(
            ratingRepository = ratingRepository,
            ratingFilterRepository = ratingFilterRepository,
            userRepository = userRepository
        )
    }

    @Provides
    fun provideUserRemoteRepository(
        instagramApi: ApiInstagram,
        userApi: UserApi,
        userDao: UserDao,
        imagesDao: UserProfileDao,
        getLocaleLanguageCodeUseCase: GetLocaleLanguageCodeUseCase
    ): IUserRepository {
        return UserRepository(
            instagramApi = instagramApi,
            userApi = userApi,
            userDao = userDao,
            imagesDao = imagesDao,
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
