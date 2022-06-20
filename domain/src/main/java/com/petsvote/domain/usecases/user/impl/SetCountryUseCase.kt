package com.petsvote.domain.usecases.user.impl

import com.petsvote.domain.entity.user.location.Country
import com.petsvote.domain.repository.IUserRepository
import com.petsvote.domain.usecases.user.ISetCountryUseCase
import javax.inject.Inject

class SetCountryUseCase @Inject constructor(
    private val userRepository: IUserRepository,
) : ISetCountryUseCase {

    override suspend fun setCountry(country: Country) {
        userRepository.setCountry(id = country.id, title = country.title)
    }
}