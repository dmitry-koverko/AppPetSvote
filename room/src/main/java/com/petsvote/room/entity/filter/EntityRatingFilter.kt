package com.petsvote.room.entity.filter

import androidx.room.Entity
import androidx.room.PrimaryKey
import retrofit2.http.Query

@Entity
data class EntityRatingFilter(
    @PrimaryKey(autoGenerate = true)
    var filterId: Int = 1,
    val type: String?,
    val sex: String?,
    val city_id: Int?,
    val country_id: Int?,
    val age_between: String?,
    val rating_type: EntityRatingFilterType = EntityRatingFilterType.GLOBAL,
    val id: Int?,
    val breed_id: Int?,
)

enum class EntityRatingFilterType(val nameParams: String){
    GLOBAL("global"),
    COUNTRY("country"),
    CITY("city")
}