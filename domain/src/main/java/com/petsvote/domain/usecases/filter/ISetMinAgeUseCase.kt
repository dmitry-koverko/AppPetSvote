package com.petsvote.domain.usecases.filter

interface ISetMinAgeUseCase {
    suspend fun setMin(age: Int)
}