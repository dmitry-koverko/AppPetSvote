package com.petsvote.domain.usecases.user

import com.petsvote.domain.entity.user.location.Country

interface IGetCountryListUseCase {

    suspend fun getCountries(): List<Country>

}