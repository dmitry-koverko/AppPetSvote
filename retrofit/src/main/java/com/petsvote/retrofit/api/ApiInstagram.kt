package com.petsvote.retrofit.api

import com.petsvote.retrofit.adapter.NetworkResponse
import com.petsvote.retrofit.entity.ApiError
import com.petsvote.retrofit.entity.InstagramResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInstagram {
    @GET("{id}/info/")
    suspend fun getUsername(
        @Header("User-Agent") userAgent: String,
        @Path("id") id: Long): NetworkResponse<InstagramResponse, ApiError>
}
