package com.petsvote.domain.repository

interface IResourcesRepository {

    fun getString(resourcesId: Int): String
    fun getStringByName(string: String): String
    fun getStringUiByName(string: String): String

}