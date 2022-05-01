package com.petsvote.domain.usecases.user.impl

import com.petsvote.domain.entity.user.DataResponse
import com.petsvote.domain.entity.user.UserInfo
import com.petsvote.domain.repository.IUserRepository
import com.petsvote.domain.usecases.user.IGetCurrentUserUseCase
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val userRepository: IUserRepository
): IGetCurrentUserUseCase {
    override suspend fun getCurrentUser(): UserInfo? {
        return userRepository.getUser()
    }
}