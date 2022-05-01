package com.petsvote.domain.usecases.configuration.impl

import com.petsvote.domain.repository.IConfigurationRepository
import com.petsvote.domain.usecases.configuration.GetPrivacyPolicyUseCase
import javax.inject.Inject

class GetPrivacyPolicyUseCaseImpl @Inject constructor(
    private val configurationRepository: IConfigurationRepository
): GetPrivacyPolicyUseCase {

    override suspend fun getPrivacyPolicy(): String {
        return configurationRepository.getPrivacyPolicy().data
    }


}