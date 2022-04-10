package com.petsvote.domain.usecases.configuration

import com.petsvote.domain.entity.configuration.Document
import com.petsvote.domain.entity.user.DataResponse
import kotlinx.coroutines.flow.Flow

interface GetPrivacyPolicyUseCase {

    suspend fun getPrivacyPolicy(): String

}