package com.petsvote.domain.repository

import com.petsvote.domain.entity.breed.Breed
import kotlinx.coroutines.flow.Flow

interface IBreedRepository {
    suspend fun getBreeds(): List<Breed>
    suspend fun updateBreeds()
    suspend fun getBreedById(id: Int): Flow<List<Breed>>
}