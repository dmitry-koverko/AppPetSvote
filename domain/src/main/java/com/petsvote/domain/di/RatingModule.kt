package com.petsvote.domain.di

import com.petsvote.domain.repository.rating.IRatingFilterRepository
import com.petsvote.domain.repository.rating.RatingPagingRepository
import com.petsvote.domain.usecases.rating.GetRatingUseCase
import com.petsvote.domain.usecases.rating.IGetRatingFilterUseCase
import com.petsvote.domain.usecases.rating.impl.GetRatingFilterUseCase
import com.petsvote.domain.usecases.rating.impl.GetRatingUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class RatingModule {

    @Provides
    fun provideGetRatingFilterUseCase(ratingFilterRepository: IRatingFilterRepository): IGetRatingFilterUseCase {
        return GetRatingFilterUseCase(ratingFilterRepository = ratingFilterRepository)
    }

    @Provides
    fun provideGetRatingUseCase(ratingRepository: RatingPagingRepository): GetRatingUseCase {
        return GetRatingUseCaseImpl(ratingRepository = ratingRepository)
    }

}