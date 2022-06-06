package com.petsvote.domain.usecases.filter

import com.petsvote.core.adapter.Item

interface ISetBreedUseCase {

    suspend fun setBreedFilter(item: Item?)

}