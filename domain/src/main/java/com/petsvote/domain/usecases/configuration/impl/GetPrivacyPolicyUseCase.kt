package com.petsvote.domain.usecases.configuration.impl

import com.petsvote.domain.repository.ConfigurationRepository
import com.petsvote.domain.usecases.configuration.GetPrivacyPolicyUseCase
import javax.inject.Inject

class GetPrivacyPolicyUseCase @Inject constructor(
    private val configurationRepository: ConfigurationRepository
): GetPrivacyPolicyUseCase {

    override suspend fun getPrivacyPolicy(): String {
        return configurationRepository.getPrivacyPolicy().data
    }


}