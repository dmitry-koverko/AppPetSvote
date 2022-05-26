package com.petsvote.retrofit.api

import com.petsvote.retrofit.SettingsApi
import com.petsvote.retrofit.adapter.NetworkResponse
import com.petsvote.retrofit.entity.ApiError
import com.petsvote.retrofit.entity.rating.Rating
import com.petsvote.retrofit.entity.rating.Vote
import retrofit2.http.*

interface RatingApi {

    @GET("get-rating-list")
    suspend fun getRating(
        @Header("Authorization") token: String,
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int?,
        @Query("lang") lang: String?,
        @Query("type") type: String?,
        @Query("sex") sex: String?,
        @Query("city_id") city_id: Int?,
        @Query("country_id") country_id: Int?,
        @Query("age_between") age_between: String?,
        @Query("rating_type") rating_type: String?,
        @Query("id") id: Int?,
        @Query("breed_id") breed_id: Int?,
    ): NetworkResponse<Rating, ApiError>

    @GET("get-pets-list")
    suspend fun getVotePets(
        @Header("Authorization") token: String,
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int?,
        @Query("lang") lang: String?,
        @Query("type") type: String?,
        @Query("sex") sex: String?,
        @Query("city_id") city_id: Int?,
        @Query("country_id") country_id: Int?,
        @Query("age_between") age_between: String?,
        @Query("rating_type") rating_type: String?,
        @Query("ids") ids: String?,
    ): NetworkResponse<Vote, ApiError>

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("add-vote")
    suspend fun addVote(
        @Header("Authorization") token: String,
        @Field("from_user_id") from_user_id: Int?,
        @Field("to_pet_id") to_pet_id: Int?,
        @Field("mark") mark: Int?,
        @Field("grant_point") grant_point: Int?,
    )

}