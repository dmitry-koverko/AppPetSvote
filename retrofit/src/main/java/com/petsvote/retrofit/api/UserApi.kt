package com.petsvote.retrofit.api

import android.service.autofill.UserData
import com.petsvote.retrofit.adapter.NetworkResponse
import com.petsvote.retrofit.entity.ApiError
import com.petsvote.retrofit.entity.user.Register
import com.petsvote.retrofit.entity.user.User
import okhttp3.MultipartBody
import retrofit2.http.*

interface UserApi {

    @POST("register-apple")
    suspend fun registerUser(@Query("code") code: String?):
            NetworkResponse<Register, ApiError>

    @GET("get-current-user")
    suspend fun getCurrentUser(
        @Query("lang") lang: String?,
    ): NetworkResponse<User, ApiError>

    @Multipart
    @POST("save-user-data")
    suspend fun saveUserData(
        @Part photo_data: MultipartBody.Part?,
        @Query("first_name") first_name: String?,
        @Query("last_name") last_name: String?,
        @Query("location") location: String?,
    ):  NetworkResponse<UserData, ApiError>


    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("logout-user")
    suspend fun logout(
        @Field("user_id") user_id: Int?
    )

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("delete-user")
    suspend fun deleteUser(
        @Field("user_id") user_id: Int?
    )

}