package com.petsvote.retrofit.api

import com.petsvote.retrofit.adapter.NetworkResponse
import com.petsvote.retrofit.entity.ApiError
import com.petsvote.retrofit.entity.pet.FindPet
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface PetApi {

    @GET("find-pet")
    suspend fun findPet(
        @Header("Authorization") token: String,
        @Query("lang") lang: String?,
        @Query("pet_id") pet_id: Int,
    ): NetworkResponse<FindPet, ApiError>

}