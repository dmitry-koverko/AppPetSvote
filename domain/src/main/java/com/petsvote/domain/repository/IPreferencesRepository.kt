package com.petsvote.domain.repository

interface IPreferencesRepository {

    suspend fun getSettingsNotify(): Boolean
    suspend fun setSettingsNotify(b: Boolean)
    suspend fun setAddPhotosUseCase()
    suspend fun getAddPhotosSettings(): Boolean

}