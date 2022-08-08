package com.petsvote.domain.usecases.filter

interface ISetBreedsUserPetUseCase {
    suspend fun setUserBredIdRatingFilter(breedId: Int?)
}