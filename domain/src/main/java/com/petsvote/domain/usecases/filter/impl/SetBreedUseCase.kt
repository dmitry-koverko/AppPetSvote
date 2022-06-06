package com.petsvote.domain.usecases.filter.impl

import com.petsvote.core.adapter.Item
import com.petsvote.domain.entity.breed.Breed
import com.petsvote.domain.repository.rating.IRatingFilterRepository
import com.petsvote.domain.usecases.filter.ISetBreedUseCase

class SetBreedUseCase(
    private val ratingFilterRepository: IRatingFilterRepository
): ISetBreedUseCase {
    override suspend fun setBreedFilter(item: Item?) {
        ratingFilterRepository.setBredIdRatingFilter(if(item == null) null else (item as Breed).breedId)
    }
}