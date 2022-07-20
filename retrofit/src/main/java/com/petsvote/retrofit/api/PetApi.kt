package com.petsvote.retrofit.api

import com.petsvote.retrofit.adapter.NetworkResponse
import com.petsvote.retrofit.entity.ApiError
import com.petsvote.retrofit.entity.pet.FindPet
import com.petsvote.retrofit.entity.pet.PetDetails
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


    @GET("get-pet-details")
    suspend fun getPetDetails(
        @Header("Authorization") token: String,
        @Query("city_id") city_id: Int?,
        @Query("country_id") country_id: Int?,
        @Query("id") id: Int?,
        @Query("user_id") user_id: Int?,
        @Query("age_between") age_between: String?,
        @Query("rating_type") rating_type: String?,
        //@Query("type") type: String?,
    ): NetworkResponse<PetDetails, ApiError>


}