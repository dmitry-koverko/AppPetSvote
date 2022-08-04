package com.petsvote.retrofit.api

import com.petsvote.retrofit.adapter.NetworkResponse
import com.petsvote.retrofit.entity.ApiError
import com.petsvote.retrofit.entity.pet.FindPet
import com.petsvote.retrofit.entity.pet.Pet
import com.petsvote.retrofit.entity.pet.PetDetails
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

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

    @Multipart
    @POST("add-pet")
    suspend fun addPet(
        @Header("Authorization") token: String,
        @Part photos:List<MultipartBody.Part>,
        @Query("bdate") bdate: String?,
        @Query("user_id") user_id: Int?,
        @Query("name") name: String?,
        @Query("breed_id") breed_id: String?,
        @Query("sex") sex: String?,
        @Query("type") type: String?,
    ): NetworkResponse<Pet, ApiError>

    @Multipart
    @POST("edit-pet")
    suspend fun editPet(
        @Header("Authorization") token: String,
        @Part photos:List<MultipartBody.Part>?,
        @Query("bdate") bdate: String?,
        @Query("user_id") user_id: Int?,
        @Query("name") name: String?,
        @Query("breed_id") breed_id: String?,
        @Query("sex") sex: String?,
        @Query("type") type: String?,
        @Body requestBody: RequestBody
    ): NetworkResponse<Pet, ApiError>


    @POST("edit-pet")
    suspend fun editPetWithoutPhotos(
        @Header("Authorization") token: String,
        @Query("bdate") bdate: String?,
        @Query("user_id") user_id: Int?,
        @Query("name") name: String?,
        @Query("breed_id") breed_id: String?,
        @Query("sex") sex: String?,
        @Query("type") type: String?,
        @Query("id") id: Int?,
    ): NetworkResponse<Pet, ApiError>

}