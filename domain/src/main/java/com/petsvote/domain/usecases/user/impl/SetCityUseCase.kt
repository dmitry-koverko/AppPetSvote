package com.petsvote.domain.usecases.user.impl

import com.petsvote.domain.entity.user.location.City
import com.petsvote.domain.repository.IUserRepository
import com.petsvote.domain.usecases.user.ISetCityUseCase
import javax.inject.Inject

class SetCityUseCase @Inject constructor(
    private val userRepository: IUserRepository,
): ISetCityUseCase {

    override suspend fun setCity(city: City) {
        userRepository.setCity(city.title, city.id, city.region ?: "")
    }
}