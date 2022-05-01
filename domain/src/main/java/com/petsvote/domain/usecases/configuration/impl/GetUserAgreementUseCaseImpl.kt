package com.petsvote.domain.usecases.configuration.impl

import com.petsvote.domain.repository.IConfigurationRepository
import com.petsvote.domain.usecases.configuration.GetUserAgreementUseCase
import javax.inject.Inject

class GetUserAgreementUseCaseImpl @Inject constructor(
    private val configurationRepository: IConfigurationRepository
): GetUserAgreementUseCase {

    override suspend fun getUserAgreement(): String {
        return configurationRepository.getUserAgreement().data
    }
}