package com.petsvote.domain.usecases.configuration

interface IGetSettingsNotifyUseCase {

    suspend fun getUserNotify(): Boolean

}