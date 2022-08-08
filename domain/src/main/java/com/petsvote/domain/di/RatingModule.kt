package com.petsvote.domain.di

import com.petsvote.domain.repository.rating.IRatingFilterRepository
import com.petsvote.domain.repository.rating.RatingPagingRepository
import com.petsvote.domain.repository.rating.RatingRepository
import com.petsvote.domain.usecases.filter.*
import com.petsvote.domain.usecases.filter.impl.*
import com.petsvote.domain.usecases.rating.GetRatingUseCase
import com.petsvote.domain.usecases.rating.IAddVoteUseCase
import com.petsvote.domain.usecases.rating.IGetVotePetsUseCase
import com.petsvote.domain.usecases.rating.impl.AddVoteUseCase
import com.petsvote.domain.usecases.rating.impl.GetRatingUseCaseImpl
import com.petsvote.domain.usecases.rating.impl.GetVotePetsUseCase
import dagger.Module
import dagger.Provides

@Module
class RatingModule {

    @Provides
    fun provideAddVoteUseCase(
        ratingRepository: RatingRepository
    ): IAddVoteUseCase {
        return AddVoteUseCase(ratingRepository = ratingRepository)
    }

    @Provides
    fun provideGetVotePetsUseCase(
        ratingRepository: RatingRepository
    ): IGetVotePetsUseCase {
        return GetVotePetsUseCase(ratingRepository = ratingRepository)
    }

    @Provides
    fun provideSetDefaultInRatingFilterUseCase(
        ratingFilterRepository: IRatingFilterRepository
    ): ISetDefaultRatingFilterUseCase {
        return SetDefaultRatingFilterUseCase(ratingFilterRepository = ratingFilterRepository)
    }

    @Provides
    fun provideSetTypeRatingFilterUseCase(
        ratingFilterRepository: IRatingFilterRepository
    ): ISetRatingFilterTypeUseCase {
        return SetRatingFilterTypeUseCase(ratingFilterRepository = ratingFilterRepository)
    }

    @Provides
    fun provideSetBreedIdInRatingFilterUseCase(
        ratingFilterRepository: IRatingFilterRepository
    ): ISetBreedIdInRatingFilterUseCase {
        return SetBreedIdInRatingFilterUseCase(ratingFilterRepository = ratingFilterRepository)
    }

    @Provides
    fun provideSetBreedsUserPetUseCase(
        ratingFilterRepository: IRatingFilterRepository
    ): ISetBreedsUserPetUseCase {
        return SetBreedsUserPetUseCase(ratingFilterRepository = ratingFilterRepository)
    }

    @Provides
    fun provideGetRatingFilterTypeUseCase(
        ratingFilterRepository: IRatingFilterRepository
    ): IGetRatingFilterTypeUseCase {
        return GetRatingFilterTypeUseCase(ratingFilterRepository = ratingFilterRepository)
    }

    @Provides
    fun provideGetRatingFilterUseCase(
        ratingFilterRepository: IRatingFilterRepository
    ): IGetRatingFilterUseCase {
        return GetRatingFilterUseCase(ratingFilterRepository = ratingFilterRepository)
    }

    @Provides
    fun provideGetRatingUseCase(ratingRepository: RatingPagingRepository): GetRatingUseCase {
        return GetRatingUseCaseImpl(ratingRepository = ratingRepository)
    }

}