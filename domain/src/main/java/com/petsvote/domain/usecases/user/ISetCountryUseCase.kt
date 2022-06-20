package com.petsvote.domain.usecases.user

import com.petsvote.domain.entity.user.location.Country

interface ISetCountryUseCase {

    suspend fun setCountry(country: Country)

}