package com.petsvote.domain.repository

import com.petsvote.domain.entity.breed.Breed
import kotlinx.coroutines.flow.Flow

interface IBreedRepository {
    suspend fun getBreeds(offset: Int, text: String, limit: Int = 50): List<Breed>
    suspend fun updateBreeds()
    suspend fun getBreedById(id: Int): Flow<List<Breed>>
    suspend fun getBreedByBreedId(id: Int?): Breed?
}