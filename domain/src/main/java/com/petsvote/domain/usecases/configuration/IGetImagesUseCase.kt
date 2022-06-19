package com.petsvote.domain.usecases.configuration

import com.petsvote.domain.entity.configuration.UserProfile
import kotlinx.coroutines.flow.Flow

interface IGetImagesUseCase {

    suspend fun getImage(): Flow<UserProfile>

}