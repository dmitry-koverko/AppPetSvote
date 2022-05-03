package com.petsvote.domain.usecases.rating

interface ISetBreedIdInRatingFilterUseCase {
    suspend fun setBredIdRatingFilter(breedId: Int?)
}