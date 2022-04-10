package com.petsvote.domain.usecases.configuration.impl

import com.petsvote.domain.entity.configuration.Document
import com.petsvote.domain.entity.user.DataResponse
import com.petsvote.domain.repository.ConfigurationRepository
import com.petsvote.domain.usecases.configuration.GetUserAgreementUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserAgreementUseCaseImpl @Inject constructor(
    private val configurationRepository: ConfigurationRepository
): GetUserAgreementUseCase {

    override suspend fun getUserAgreement(): String {
        return configurationRepository.getUserAgreement().data
    }
}