package com.petsvote.data.repository

import com.petsvote.data.mappers.checkResult
import com.petsvote.data.mappers.toDocument
import com.petsvote.data.mappers.toUserInfoUC
import com.petsvote.domain.entity.configuration.Document
import com.petsvote.domain.repository.ConfigurationRepository
import com.petsvote.domain.usecases.configuration.GetLocaleLanguageCodeUseCase
import com.petsvote.retrofit.api.ConfigurationApi
import com.petsvote.retrofit.entity.user.Register
import javax.inject.Inject

class ConfigurationRepository @Inject constructor(
    private val configurationApi: ConfigurationApi,
    private val getLocaleLanguageCodeUseCase: GetLocaleLanguageCodeUseCase,
) : ConfigurationRepository {

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