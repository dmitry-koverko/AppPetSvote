package com.petsvote.retrofit.entity.rating

import android.os.Build
import android.util.Log
import com.cesarferreira.tempo.Tempo
import com.cesarferreira.tempo.minus
import com.cesarferreira.tempo.toDate
import com.petsvote.retrofit.entity.Photo
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.Month
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit

@Serializable
data class VoteRating(
    val temp_type: String,
    val id: Int,
    val pet_id: Int,
    val bdate: String,
    val name: String,
    val country_name: String,
    val city_name: String,
    val sex: String,
    val breed_id: Int,
    val type: String,
    val photos: List<Photo>,
)

