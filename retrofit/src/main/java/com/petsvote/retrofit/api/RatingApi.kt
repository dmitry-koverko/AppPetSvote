package com.petsvote.retrofit.api

import com.petsvote.retrofit.adapter.NetworkResponse
import com.petsvote.retrofit.entity.ApiError
import com.petsvote.retrofit.entity.rating.Rating
import retrofit2.http.GET
import retrofit2.http.Query

interface RatingApi {

    @GET("get-rating-list")
    suspend fun getRating(
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

}