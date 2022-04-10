package com.petsvote.domain.usecases.user.impl

import com.petsvote.domain.entity.user.UserInfo
import com.petsvote.domain.repository.UserRepository
import com.petsvote.domain.usecases.user.SaveUserToLocalUseCase
import javax.inject.Inject

class SaveUserToLocalUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
): SaveUserToLocalUseCase {

    override suspend fun saveUserToLocal(user: UserInfo) {
        userRepository.saveUserToLocal(user)
    }
}