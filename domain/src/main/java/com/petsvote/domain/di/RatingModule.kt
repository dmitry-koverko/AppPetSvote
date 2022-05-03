package com.petsvote.domain.di

import com.petsvote.domain.repository.rating.IRatingFilterRepository
import com.petsvote.domain.repository.rating.RatingPagingRepository
import com.petsvote.domain.usecases.rating.GetRatingUseCase
import com.petsvote.domain.usecases.rating.IGetRatingFilterUseCase
import com.petsvote.domain.usecases.rating.ISetBreedIdInRatingFilterUseCase
import com.petsvote.domain.usecases.rating.ISetDefaultRatingFilterUseCase
import com.petsvote.domain.usecases.rating.impl.GetRatingFilterUseCase
import com.petsvote.domain.usecases.rating.impl.GetRatingUseCaseImpl
import com.petsvote.domain.usecases.rating.impl.SetBreedIdInRatingFilterUseCase
import com.petsvote.domain.usecases.rating.impl.SetDefaultRatingFilterUseCase
import dagger.Module
import dagger.Provides

@Module
class RatingModule {

    @Provides
    fun provideSetDefaultInRatingFilterUseCase(
        ratingFilterRepository: IRatingFilterRepository): ISetDefaultRatingFilterUseCase {
        return SetDefaultRatingFilterUseCase(ratingFilterRepository = ratingFilterRepository)
    }

    @Provides
    fun provideSetBreedIdInRatingFilterUseCase(
        ratingFilterRepository: IRatingFilterRepository): ISetBreedIdInRatingFilterUseCase {
        return SetBreedIdInRatingFilterUseCase(ratingFilterRepository = ratingFilterRepository)
    }

    @Provides
    fun provideGetRatingFilterUseCase(ratingFilterRepository: IRatingFilterRepository): IGetRatingFilterUseCase {
        return GetRatingFilterUseCase(ratingFilterRepository = ratingFilterRepository)
    }

    @Provides
    fun provideGetRatingUseCase(ratingRepository: RatingPagingRepository): GetRatingUseCase {
        return GetRatingUseCaseImpl(ratingRepository = ratingRepository)
    }

}