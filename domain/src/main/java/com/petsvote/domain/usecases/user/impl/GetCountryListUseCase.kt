package com.petsvote.domain.usecases.user.impl

import com.petsvote.domain.entity.user.location.Country
import com.petsvote.domain.repository.IUserRepository
import com.petsvote.domain.usecases.user.IGetCountryListUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class GetCountryListUseCase @Inject constructor(
    private val userRepository: IUserRepository,
): IGetCountryListUseCase {

    override suspend fun getCountries(): List<Country> {
        var user = userRepository.getCurrentUser()
        var userProfile = userRepository.getUserProfile()
        var list = userRepository.getCountryList()

        if(userProfile?.locationCountryId != null) list.find { (it as Country).id == userProfile?.locationCountryId }?.isSelect = true
        else list.find { (it as Country).id == user.location?.country_id }?.isSelect = true

        return list
    }
}