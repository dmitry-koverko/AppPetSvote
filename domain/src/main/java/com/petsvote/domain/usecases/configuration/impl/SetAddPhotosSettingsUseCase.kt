package com.petsvote.domain.usecases.configuration.impl

import com.petsvote.domain.repository.IPreferencesRepository
import com.petsvote.domain.usecases.configuration.ISetAddPhotosSettingsUseCase
import com.petsvote.domain.usecases.configuration.ISetSettingsNotifyUseCase
import javax.inject.Inject

class SetAddPhotosSettingsUseCase @Inject constructor(
    private val preferencesRepository: IPreferencesRepository
): ISetAddPhotosSettingsUseCase {
    override suspend fun setAddPhotosSettings() {
        preferencesRepository.setAddPhotosUseCase()
    }

}