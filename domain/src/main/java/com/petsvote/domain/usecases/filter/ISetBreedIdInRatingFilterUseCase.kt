package com.petsvote.domain.usecases.filter

interface ISetBreedIdInRatingFilterUseCase {
    suspend fun setBredIdRatingFilter(breedId: Int?)
}