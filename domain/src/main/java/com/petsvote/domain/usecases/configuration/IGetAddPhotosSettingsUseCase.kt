package com.petsvote.domain.usecases.configuration

interface IGetAddPhotosSettingsUseCase {
    suspend fun getAddPhotosSettings(): Boolean
}