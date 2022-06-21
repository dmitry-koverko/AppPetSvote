package com.petsvote.domain.usecases.user

import com.petsvote.domain.entity.user.location.City

interface ISetCityUseCase {

    suspend fun setCity(city: City)

}