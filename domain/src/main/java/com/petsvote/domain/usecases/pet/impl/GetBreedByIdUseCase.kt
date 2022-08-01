package com.petsvote.domain.usecases.pet.impl

import com.petsvote.domain.repository.IBreedRepository
import com.petsvote.domain.repository.IUserRepository
import com.petsvote.domain.usecases.pet.IGetBreedByIdUseCase
import javax.inject.Inject

class GetBreedByIdUseCase @Inject constructor(
    private val breedRepository: IBreedRepository,
    private val userRepository: IUserRepository
): IGetBreedByIdUseCase {
    override suspend fun getBreedInfo(breedId: Int): String {
        val breedName = breedRepository.getBreedByBreedId(breedId)?.breedName
        val locale = userRepository.getCurrentUser().location
//        val localeString =
//            if (locale?.city_id != null && locale.city_id != -1) ",${locale.country}, ${locale.city}" else ""
        return "$breedName"// $localeString"
    }
}