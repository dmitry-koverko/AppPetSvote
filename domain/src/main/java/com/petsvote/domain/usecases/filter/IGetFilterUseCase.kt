package com.petsvote.domain.usecases.filter

import com.petsvote.domain.entity.filter.Filter
import kotlinx.coroutines.flow.Flow

interface IGetFilterUseCase {

    suspend fun getFilter(): Flow<Filter>
}