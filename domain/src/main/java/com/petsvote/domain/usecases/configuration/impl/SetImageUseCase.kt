package com.petsvote.domain.usecases.configuration.impl

import com.petsvote.domain.repository.IUserRepository
import com.petsvote.domain.usecases.configuration.ISetImageUseCase
import javax.inject.Inject

class SetImageUseCase @Inject constructor(
    private val userRepository: IUserRepository
):ISetImageUseCase {
    override suspend fun setImage(bytes: ByteArray) {
        userRepository.setImage(bytes)
    }
}