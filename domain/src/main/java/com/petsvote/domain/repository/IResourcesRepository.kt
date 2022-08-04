package com.petsvote.domain.repository

interface IResourcesRepository {

    fun getString(resourcesId: Int, vararg input: String): String
    fun getStringByName(string: String, vararg input: String): String
    fun getStringUiByName(string: String, vararg input: String): String

}