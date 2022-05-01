package com.petsvote.data.repository

import com.petsvote.data.mappers.checkResult
import com.petsvote.data.mappers.toDocument
import com.petsvote.domain.entity.configuration.Document
import com.petsvote.domain.repository.IConfigurationRepository
import com.petsvote.domain.usecases.configuration.GetLocaleLanguageCodeUseCase
import com.petsvote.retrofit.api.ConfigurationApi
import javax.inject.Inject

class ConfigurationRepository @Inject constructor(
    private val configurationApi: ConfigurationApi,
    private val getLocaleLanguageCodeUseCase: GetLocaleLanguageCodeUseCase,
) : IConfigurationRepository {

    override suspend fun getUserAgreement(): Document {
        return checkResult(configurationApi
            .getTerms(lang = getLocaleLanguageCodeUseCase.getLanguage()))?.toDocument()
            ?: Document("")
    }

    override suspend fun getPrivacyPolicy(): Document {
        return checkResult(configurationApi
            .getPolicy(lang = getLocaleLanguageCodeUseCase.getLanguage()))?.toDocument()
            ?: Document("")
    }
}