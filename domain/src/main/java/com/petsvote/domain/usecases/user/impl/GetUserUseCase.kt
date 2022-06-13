package com.petsvote.domain.usecases.user.impl

import com.petsvote.domain.entity.user.UserInfo
import com.petsvote.domain.repository.IUserRepository
import com.petsvote.domain.usecases.user.IGetUserUseCase
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: IUserRepository
): IGetUserUseCase {
    override suspend fun getUser(): UserInfo {
        return userRepository.getCurrentUser()
    }
}