package com.petsvote.domain.di

import android.content.res.Configuration
import com.petsvote.domain.repository.ConfigurationRepository
import com.petsvote.domain.repository.UserRepository
import com.petsvote.domain.usecases.configuration.GetLocaleLanguageCodeUseCase
import com.petsvote.domain.usecases.configuration.GetPrivacyPolicyUseCase
import com.petsvote.domain.usecases.configuration.GetUserAgreementUseCase
import com.petsvote.domain.usecases.configuration.impl.GetLocaleLanguageCodeUseCaseImpl
import com.petsvote.domain.usecases.configuration.impl.GetPrivacyPolicyUseCaseImpl
import com.petsvote.domain.usecases.configuration.impl.GetUserAgreementUseCaseImpl
import com.petsvote.domain.usecases.user.CheckLoginUserUseCase
import com.petsvote.domain.usecases.user.RegisterUserUseCase
import com.petsvote.domain.usecases.user.impl.CheckLoginUserUseCaseImpl
import com.petsvote.domain.usecases.user.impl.RegisterUserUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class ConfigurationModule {

    @Provides
    fun provideGetLocaleLanguageCodeUseCase(configuration: Configuration): GetLocaleLanguageCodeUseCase {
        return GetLocaleLanguageCodeUseCaseImpl(configuration = configuration)
    }

    @Provides
    fun provideGetPrivacyPolicyUseCase(configurationRepository: ConfigurationRepository): GetPrivacyPolicyUseCase {
        return GetPrivacyPolicyUseCaseImpl(configurationRepository = configurationRepository)
    }

    @Provides
    fun provideGetUserAgreementUseCase(configurationRepository: ConfigurationRepository): GetUserAgreementUseCase {
        return GetUserAgreementUseCaseImpl(configurationRepository = configurationRepository)
    }


}