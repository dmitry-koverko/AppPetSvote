package com.petsvote.domain.usecases.pet.create.impl

import com.petsvote.domain.repository.IPetRepository
import com.petsvote.domain.repository.IUserRepository
import com.petsvote.domain.usecases.pet.create.IGetInstagramUserNameUseCase
import javax.inject.Inject

class GetInstagramUserNameUseCase @Inject constructor(
    private val userRepository: IUserRepository
): IGetInstagramUserNameUseCase {
    override suspend fun getUsername(id: Long): String {
        return userRepository.getUsernameInsta(id)
    }
}