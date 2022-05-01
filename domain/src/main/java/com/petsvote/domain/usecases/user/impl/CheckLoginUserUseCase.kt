package com.petsvote.domain.usecases.user.impl

import com.petsvote.domain.repository.IUserRepository
import com.petsvote.domain.usecases.user.ICheckLoginUserUseCase
import javax.inject.Inject

class CheckLoginUserUseCase @Inject constructor(
    private val IUserRepository: IUserRepository
) : ICheckLoginUserUseCase {

    override suspend fun checkLoginUser(): Boolean {
        return IUserRepository.checkLoginUser()
    }

}