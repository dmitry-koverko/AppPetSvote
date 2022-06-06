package com.petsvote.domain.usecases.configuration

import com.petsvote.domain.entity.breed.Breed

interface IGetBreedsUseCase {

    suspend fun updateBreeds()
    suspend fun getBreeds(): List<Breed>
}