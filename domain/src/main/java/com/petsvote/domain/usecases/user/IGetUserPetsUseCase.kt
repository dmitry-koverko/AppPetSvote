package com.petsvote.domain.usecases.user

import com.petsvote.domain.entity.user.UserPet
import kotlinx.coroutines.flow.Flow

interface IGetUserPetsUseCase {

    suspend fun getUserPets(): Flow<List<UserPet>>

}