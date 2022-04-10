package com.petsvote.domain.usecases.configuration

import com.petsvote.domain.entity.configuration.Document
import com.petsvote.domain.entity.user.DataResponse
import com.petsvote.domain.entity.user.RegisterUserParams
import com.petsvote.domain.entity.user.UserInfo
import kotlinx.coroutines.flow.Flow

interface GetUserAgreementUseCase {

    suspend fun getUserAgreement(): String

}