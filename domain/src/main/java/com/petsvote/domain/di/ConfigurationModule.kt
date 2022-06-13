package com.petsvote.domain.di

import android.content.res.Configuration
import com.petsvote.domain.repository.IConfigurationRepository
import com.petsvote.domain.repository.IPreferencesRepository
import com.petsvote.domain.usecases.configuration.*
import com.petsvote.domain.usecases.configuration.impl.*
import dagger.Module
import dagger.Provides

@Module
class ConfigurationModule {

    @Provides
    fun provideSetSettingsNotifyUseCase(preferencesRepository: IPreferencesRepository): ISetSettingsNotifyUseCase {
        return SetSettingsNotifyUseCase(preferencesRepository = preferencesRepository)
    }

    @Provides
    fun provideGetSettingsNotifyUseCase(preferencesRepository: IPreferencesRepository): IGetSettingsNotifyUseCase {
        return GetSettingsNotifyUseCase(preferencesRepository = preferencesRepository)
    }

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