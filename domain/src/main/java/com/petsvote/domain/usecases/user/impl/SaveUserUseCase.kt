package com.petsvote.domain.usecases.user.impl

import com.petsvote.domain.entity.user.SaveUserParams
import com.petsvote.domain.repository.IUserRepository
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val userRepository: IUserRepository,
): ISaveUserUseCase {

    override suspend fun saveUser(params: SaveUserParams) {
        userRepository.saveUser(params)
    }
}