package com.petsvote.domain.usecases.user.impl

import com.petsvote.domain.repository.IUserRepository
import com.petsvote.domain.usecases.user.ISetEmptyUserProfileUseCase
import javax.inject.Inject

class SetEmptyUserProfileUseCase @Inject constructor(
    private val userRepository: IUserRepository,
): ISetEmptyUserProfileUseCase {

    override suspend fun setEmptyUserProfile() {
        userRepository.setEmptyUserProfile()
    }
}