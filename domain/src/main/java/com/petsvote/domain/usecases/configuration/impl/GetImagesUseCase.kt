package com.petsvote.domain.usecases.configuration.impl

import com.petsvote.domain.entity.configuration.UserProfile
import com.petsvote.domain.repository.IUserRepository
import com.petsvote.domain.usecases.configuration.IGetImagesUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(
    private val userRepository: IUserRepository
): IGetImagesUseCase {
    override suspend fun getImage(): Flow<UserProfile> {
        return userRepository.getImage()
    }
}