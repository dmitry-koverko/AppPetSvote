package com.petsvote.domain.di

import android.content.res.Configuration
import com.petsvote.domain.repository.RatingRepository
import com.petsvote.domain.usecases.configuration.GetLocaleLanguageCodeUseCase
import com.petsvote.domain.usecases.configuration.impl.GetLocaleLanguageCodeUseCaseImpl
import com.petsvote.domain.usecases.rating.GetRatingUseCase
import com.petsvote.domain.usecases.rating.impl.GetRatingUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class RatingModule {

    @Provides
    fun provideGetRatingUseCase(ratingRepository: RatingRepository): GetRatingUseCase {
        return GetRatingUseCaseImpl(ratingRepository = ratingRepository)
    }

}