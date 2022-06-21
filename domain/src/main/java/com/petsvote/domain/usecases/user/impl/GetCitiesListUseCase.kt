package com.petsvote.domain.usecases.user.impl

import com.petsvote.domain.entity.user.location.City
import com.petsvote.domain.entity.user.location.Country
import com.petsvote.domain.repository.IUserRepository
import com.petsvote.domain.usecases.user.IGetCitiesListUseCase
import javax.inject.Inject

class GetCitiesListUseCase @Inject constructor(
    private val userRepository: IUserRepository,
): IGetCitiesListUseCase {

    override suspend fun getCities(): List<City> {
        var user = userRepository.getCurrentUser()
        var userProfile = userRepository.getUserProfile()
        var list = userRepository.getCitiesList()

        if(userProfile?.locationCityId != null) list.find { (it as City).id == userProfile.locationCityId }?.isSelect = true
        else list.find { (it as City).id == user.location?.city_id }?.isSelect = true

        return list
    }
}