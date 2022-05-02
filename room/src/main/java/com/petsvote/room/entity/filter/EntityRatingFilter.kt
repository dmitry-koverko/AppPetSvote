package com.petsvote.room.entity.filter

import androidx.room.Entity
import retrofit2.http.Query

@Entity
data class EntityRatingFilter(
    val type: String?,
    val sex: String?,
    val city_id: Int?,
    val country_id: Int?,
    val age_between: String?,
    val rating_type: String?,
    val id: Int?,
    val breed_id: Int?,
)