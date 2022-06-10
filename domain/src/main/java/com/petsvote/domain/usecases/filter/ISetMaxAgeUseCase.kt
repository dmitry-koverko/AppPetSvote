package com.petsvote.domain.usecases.filter

interface ISetMaxAgeUseCase {
    suspend fun setMax(age: Int)
}