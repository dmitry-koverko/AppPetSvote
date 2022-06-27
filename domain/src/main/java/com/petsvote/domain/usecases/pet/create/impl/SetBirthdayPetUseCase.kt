package com.petsvote.domain.usecases.pet.create.impl

import com.petsvote.domain.repository.IPetRepository
import com.petsvote.domain.usecases.pet.create.ISetBirthdayPetUseCase
import javax.inject.Inject

class SetBirthdayPetUseCase @Inject constructor(
    private val petRepository: IPetRepository
): ISetBirthdayPetUseCase {
    override suspend fun setBirthdayPet(date: String) {
        petRepository.setSelectBirthday(date)
    }
}