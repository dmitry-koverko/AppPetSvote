package com.petsvote.domain.usecases.user.impl

import com.petsvote.domain.repository.UserRepository
import com.petsvote.domain.usecases.user.CheckLoginUserUseCase
import javax.inject.Inject

class CheckLoginUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : CheckLoginUserUseCase {

    override suspend fun checkLoginUser(): Boolean {
        return userRepository.checkLoginUser()
    }

}