package com.petsvote.domain.di

import android.content.res.Resources
import com.petsvote.domain.repository.IBreedRepository
import com.petsvote.domain.repository.IPetRepository
import com.petsvote.domain.repository.IResourcesRepository
import com.petsvote.domain.repository.IUserRepository
import com.petsvote.domain.repository.breeds.IBreedsPagingRepository
import com.petsvote.domain.repository.rating.IRatingFilterRepository
import com.petsvote.domain.usecases.configuration.IGetBreedsUseCase
import com.petsvote.domain.usecases.configuration.impl.GetBreedsUseCase
import com.petsvote.domain.usecases.filter.*
import com.petsvote.domain.usecases.filter.impl.*
import com.petsvote.domain.usecases.resources.GetStringResourcesUseCase
import com.petsvote.domain.usecases.resources.impl.GetStringResourcesUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class FilterModule {


    @Provides
    fun provideSetInsertDefaultRatingFilterUseCase(
        ratingFilterRepository: IRatingFilterRepository
    ): ISetInsertDefaultRatingFilterUseCase {
        return SetInsertDefaultRatingFilterUseCase(
            ratingFilterRepository = ratingFilterRepository
        )
    }

    @Provides
    fun provideGetRatingFilterTextUseCase(
        ratingFilterRepository: IRatingFilterRepository,
        resourcesRepository: IResourcesRepository,
        userRepository: IUserRepository,
        breedRepository: IBreedRepository
    ): IGetRatingFilterTextUseCase {
        return GetRatingFilterTextUseCase(
            ratingFilterRepository = ratingFilterRepository,
            resourcesRepository = resourcesRepository,
            userRepository = userRepository,
            breedRepository = breedRepository
        )
    }

    @Provides
    fun provideGetPagingBreedsUseCase(
        breedsPagingRepository: IBreedsPagingRepository
    ): IGetBreedsPagingUseCase {
        return GetBreedsPagingUseCase(
            breedsPagingRepository = breedsPagingRepository
        )
    }

    @Provides
    fun provideSetMinFilter(
        ratingFilterRepository: IRatingFilterRepository
    ): ISetMinAgeUseCase {
        return SetMinAgeUseCase(
            ratingFilterRepository = ratingFilterRepository
        )
    }

    @Provides
    fun provideSetMaxFilter(
        ratingFilterRepository: IRatingFilterRepository
    ): ISetMaxAgeUseCase {
        return SetMaxAgeUseCase(
            ratingFilterRepository = ratingFilterRepository
        )
    }

    @Provides
    fun provideSetSexFilter(
        ratingFilterRepository: IRatingFilterRepository
    ): ISetSexUseCase {
        return SetSexUseCase(
            ratingFilterRepository = ratingFilterRepository
        )
    }

    @Provides
    fun provideFilter(
        resourcesRepository: IResourcesRepository,
        ratingFilterRepository: IRatingFilterRepository,
        breedRepository: IBreedRepository,
        getKindsUseCase: IGetKindsUseCase
    ): IGetFilterUseCase {
        return GetFilterUseCase(
            resourcesRepository = resourcesRepository,
            ratingFilterRepository = ratingFilterRepository,
            breedRepository = breedRepository,
            getKindsUseCase = getKindsUseCase
        )
    }

    @Provides
    fun provideKinds(
        resourcesRepository: IResourcesRepository,
        ratingFilterRepository: IRatingFilterRepository,
        petRepository: IPetRepository
    ): IGetKindsUseCase {
        return GetKindsUseCase(
            resourcesRepository = resourcesRepository,
            ratingFilterRepository = ratingFilterRepository,
            petRepository = petRepository
        )
    }

    @Provides
    fun provideSetKindsRatingFilterUseCase(
        ratingFilterRepository: IRatingFilterRepository
    ): ISetKindsRatingFilterUseCase {
        return SetKindsRatingFilterUseCase(ratingFilterRepository = ratingFilterRepository)
    }

    @Provides
    fun provideSetBreedRatingFilterUseCase(
        ratingFilterRepository: IRatingFilterRepository
    ): ISetBreedUseCase {
        return SetBreedUseCase(ratingFilterRepository = ratingFilterRepository)
    }

    @Provides
    fun provideGetBreedsUseCase(
        breedRepository: IBreedRepository
    ): IGetBreedsUseCase {
        return GetBreedsUseCase(breedsRepository = breedRepository)
    }

}