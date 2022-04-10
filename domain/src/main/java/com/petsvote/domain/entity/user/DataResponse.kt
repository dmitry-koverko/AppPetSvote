package com.petsvote.domain.entity.user

sealed class DataResponse<out T: Any> {

    data class Success<T : Any>(val data: T): DataResponse<T>()
    object Error: DataResponse<Nothing>()
    object Loading : DataResponse<Nothing>()

}