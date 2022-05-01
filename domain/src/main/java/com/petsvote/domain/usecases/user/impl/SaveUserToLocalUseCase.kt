package com.petsvote.domain.usecases.user.impl

import com.petsvote.domain.entity.user.UserInfo
import com.petsvote.domain.repository.IUserRepository
import com.petsvote.domain.usecases.user.ISaveUserToLocalUseCase
import javax.inject.Inject

class SaveUserToLocalUseCase @Inject constructor(
    private val IUserRepository: IUserRepository
): ISaveUserToLocalUseCase {

    override suspend fun saveUserToLocal(user: UserInfo) {
        IUserRepository.saveUserToLocal(user)
    }
}