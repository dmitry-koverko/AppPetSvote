package com.petsvote.domain.usecases.user.impl

import com.petsvote.domain.entity.user.DataResponse
import com.petsvote.domain.entity.user.RegisterUserParams
import com.petsvote.domain.entity.user.UserInfo
import com.petsvote.domain.repository.UserRepository
import com.petsvote.domain.usecases.user.RegisterUserUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : RegisterUserUseCase {
    override suspend fun registerUser(params: RegisterUserParams): Flow<DataResponse<UserInfo>> =
        flow {
            run {
                emit(DataResponse.Loading)
                val data = userRepository.registerUser(params)
                if(data != null) emit(DataResponse.Success<UserInfo>(data))
                else emit(DataResponse.Error)
            }
        }
}