package com.petsvote.domain.usecases.filter

import kotlinx.coroutines.flow.Flow

interface IGetRatingFilterTextUseCase {

    suspend fun getFilter(): Flow<String>

}