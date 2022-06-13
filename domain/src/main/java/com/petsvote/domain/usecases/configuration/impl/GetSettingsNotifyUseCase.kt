package com.petsvote.domain.usecases.configuration.impl

import com.petsvote.domain.usecases.configuration.IGetSettingsNotifyUseCase
import com.petsvote.domain.repository.IPreferencesRepository
import javax.inject.Inject

class GetSettingsNotifyUseCase @Inject constructor(
    val preferencesRepository: IPreferencesRepository
): IGetSettingsNotifyUseCase {

    override suspend fun getUserNotify(): Boolean {
        return preferencesRepository.getSettingsNotify()
    }
}