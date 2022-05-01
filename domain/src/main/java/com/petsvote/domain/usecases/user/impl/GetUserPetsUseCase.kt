package com.petsvote.domain.usecases.user.impl

import com.petsvote.domain.entity.user.UserPet
import com.petsvote.domain.repository.IUserRepository
import com.petsvote.domain.usecases.user.IGetUserPetsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserPetsUseCase @Inject constructor(
    private val userRepository: IUserRepository
): IGetUserPetsUseCase {
    override suspend fun getUserPets(): Flow<List<UserPet>> {
        return userRepository.getUserPets()
    }
}