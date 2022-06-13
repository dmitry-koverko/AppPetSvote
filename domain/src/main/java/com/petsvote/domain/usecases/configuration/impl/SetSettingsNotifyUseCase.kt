package com.petsvote.domain.usecases.configuration.impl

import com.petsvote.domain.repository.IPreferencesRepository
import com.petsvote.domain.usecases.configuration.ISetSettingsNotifyUseCase
import javax.inject.Inject

class SetSettingsNotifyUseCase @Inject constructor(
    private val preferencesRepository: IPreferencesRepository
): ISetSettingsNotifyUseCase {
    override suspend fun setNotify(b: Boolean) {
        preferencesRepository.setSettingsNotify(b)
    }
}