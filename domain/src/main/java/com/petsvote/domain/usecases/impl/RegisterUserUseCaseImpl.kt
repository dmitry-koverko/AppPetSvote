package com.petsvote.domain.usecases.impl

import android.util.Log
import com.petsvote.domain.entity.user.RegisterUserParams
import com.petsvote.domain.repository.UserRemoteRepository
import com.petsvote.domain.usecases.RegisterUserUseCase
import javax.inject.Inject

class RegisterUserUseCaseImpl @Inject constructor(
    private val userRemoteRepository: UserRemoteRepository
): RegisterUserUseCase {
    override suspend fun getData() {
        Log.d("RegisterUserUseCaseImpl", "getData()")
        userRemoteRepository.registerUser(RegisterUserParams("fdsfdsf"))
    }
}