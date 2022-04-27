package com.petsvote.domain.di

import com.petsvote.domain.repository.rating.RatingPagingRepository
import com.petsvote.domain.usecases.rating.GetRatingUseCase
import com.petsvote.domain.usecases.rating.impl.GetRatingUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class RatingModule {

    @Provides
    fun provideGetRatingUseCase(ratingRepository: RatingPagingRepository): GetRatingUseCase {
        return GetRatingUseCaseImpl(ratingRepository = ratingRepository)
    }

}