package com.petsvote.domain.usecases.resources

interface GetStringResourcesUseCase {

    fun getString(id: Int, vararg input: String): String
    fun getString(name: String, vararg input: String): String

}