package com.petsvote.domain.usecases.pet

interface IGetBreedByIdUseCase {
    suspend fun getBreedInfo(breedId: Int): String
}