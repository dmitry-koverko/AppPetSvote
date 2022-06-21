package com.petsvote.domain.usecases.user

import com.petsvote.domain.entity.user.location.City

interface IGetCitiesListUseCase {

    suspend fun getCities(): List<City>

}