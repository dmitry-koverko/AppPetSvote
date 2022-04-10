package com.petsvote.retrofit.api

import com.petsvote.retrofit.adapter.NetworkResponse
import com.petsvote.retrofit.entity.ApiError
import com.petsvote.retrofit.entity.configuration.Document
import retrofit2.http.GET
import retrofit2.http.Query

interface ConfigurationApi {

    @GET("useragreement")
    suspend fun getTerms(@Query("lang") lang: String?,)
            : NetworkResponse<Document, ApiError>

    @GET("privacy-policy")
    suspend fun getPolicy(@Query("lang") lang: String?,)
            :  NetworkResponse<Document, ApiError>

}