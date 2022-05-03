package com.petsvote.domain.usecases.user.impl

import com.petsvote.domain.repository.IUserRepository
import com.petsvote.domain.usecases.user.ICheckLocationUserUseCase
import javax.inject.Inject

class CheckLocationUserUseCase @Inject constructor(
    private val userRepository: IUserRepository
): ICheckLocationUserUseCase {
    override suspend fun checkLocationUser(): Boolean {
        val userLocation = userRepository.getCurrentUser().location
        return if (userLocation?.city_id != null && userLocation.city_id != 0) true else false
    }
}