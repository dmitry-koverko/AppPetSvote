package com.petsvote.domain.entity.user

sealed class DataResponse<out T: Any> {

    data class Success<T : Any>(val data: T): DataResponse<T>()
    data class Error<T : Any>(val error: T): DataResponse<T>()
    object Loading : DataResponse<Nothing>()

}