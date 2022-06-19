package com.petsvote.domain.usecases.configuration.impl

import com.petsvote.domain.repository.IPreferencesRepository
import com.petsvote.domain.usecases.configuration.IGetAddPhotosSettingsUseCase
import com.petsvote.domain.usecases.configuration.ISetAddPhotosSettingsUseCase
import javax.inject.Inject

class GetAddPhotosSettingsUseCase @Inject constructor(
    private val preferencesRepository: IPreferencesRepository
): IGetAddPhotosSettingsUseCase {
    override suspend fun getAddPhotosSettings(): Boolean {
        return preferencesRepository.getSettingsNotify()
    }


}