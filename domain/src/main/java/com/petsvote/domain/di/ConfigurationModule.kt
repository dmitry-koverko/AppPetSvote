package com.petsvote.domain.di

import android.content.res.Configuration
import com.petsvote.domain.repository.IBreedRepository
import com.petsvote.domain.repository.IConfigurationRepository
import com.petsvote.domain.repository.rating.IRatingFilterRepository
import com.petsvote.domain.usecases.configuration.GetLocaleLanguageCodeUseCase
import com.petsvote.domain.usecases.configuration.GetPrivacyPolicyUseCase
import com.petsvote.domain.usecases.configuration.GetUserAgreementUseCase
import com.petsvote.domain.usecases.configuration.IGetBreedsUseCase
import com.petsvote.domain.usecases.configuration.impl.GetBreedsUseCase
import com.petsvote.domain.usecases.configuration.impl.GetLocaleLanguageCodeUseCaseImpl
import com.petsvote.domain.usecases.configuration.impl.GetPrivacyPolicyUseCaseImpl
import com.petsvote.domain.usecases.configuration.impl.GetUserAgreementUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class ConfigurationModule {

    @Provides
    fun provideGetLocaleLanguageCodeUseCase(configuration: Configuration): GetLocaleLanguageCodeUseCase {
        return GetLocaleLanguageCodeUseCaseImpl(configuration = configuration)
    }

    @Provides
    fun provideGetPrivacyPolicyUseCase(configurationRepository: IConfigurationRepository): GetPrivacyPolicyUseCase {
        return GetPrivacyPolicyUseCaseImpl(configurationRepository = configurationRepository)
    }

    @Provides
    fun provideGetUserAgreementUseCase(configurationRepository: IConfigurationRepository): GetUserAgreementUseCase {
        return GetUserAgreementUseCaseImpl(configurationRepository = configurationRepository)
    }


}