package com.petsvote.domain.usecases.user.impl

import com.petsvote.domain.entity.user.SaveUserParams

interface ISaveUserUseCase {

    suspend fun saveUser(params: SaveUserParams)

}