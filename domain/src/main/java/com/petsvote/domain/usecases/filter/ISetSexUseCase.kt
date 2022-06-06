package com.petsvote.domain.usecases.filter

interface ISetSexUseCase {

    suspend fun setSex(sex: Int)

}