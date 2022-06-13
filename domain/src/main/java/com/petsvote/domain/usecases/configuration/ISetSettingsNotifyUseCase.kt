package com.petsvote.domain.usecases.configuration

interface ISetSettingsNotifyUseCase {
    suspend fun setNotify(b: Boolean)
}