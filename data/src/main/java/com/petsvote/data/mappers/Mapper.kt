package com.petsvote.data.mappers

import android.util.Log
import com.petsvote.retrofit.adapter.NetworkResponse
import com.petsvote.retrofit.entity.ApiError

@Suppress("UNCHECKED_CAST")
fun <T : Any> checkResult(result: NetworkResponse<T, ApiError>): T?{
    val TAG = "CheckResult from Api"
    when(result){
        is NetworkResponse.ApiError -> {
            Log.d(TAG, result.toString())
            return null
        }
        is NetworkResponse.NetworkError -> {
            Log.d(TAG, result.toString())
            return null
        }
        is NetworkResponse.UnknownError -> {
            Log.d(TAG, result.toString())
            return null
        }
        is NetworkResponse.Success -> return result.body
        else -> return null
    }
}

fun <T : Any> checkResultPaging(result: NetworkResponse<T, ApiError>): T?{
    val TAG = "CheckResult from Api"
    when(result){
        is NetworkResponse.ApiError -> {
            Log.d(TAG, result.toString())
            return null
        }
        is NetworkResponse.NetworkError -> {
            Log.d(TAG, result.toString())
            return null
        }
        is NetworkResponse.UnknownError -> {
            Log.d(TAG, result.toString())
            return null
        }
        is NetworkResponse.Success -> return result.body
        else -> return null
    }
}
