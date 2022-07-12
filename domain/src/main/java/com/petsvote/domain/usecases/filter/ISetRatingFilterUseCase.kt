package com.petsvote.domain.usecases.filter

interface ISetRatingFilterUseCase {

    suspend fun setRatingFilter(
        sex: String,
    )

}