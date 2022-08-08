package com.petsvote.domain.entity.filter

data class RatingFilter(
    val type: String?,
    val sex: String?,
    val city_id: Int?,
    val country_id: Int?,
    val age_between_max: Int?,
    val age_between_min: Int?,
    val rating_type: RatingFilterType? = RatingFilterType.GLOBAL,
    val id: Int?,
    val breed_id: Int?,
    val breed_id_find: Int?
)

enum class RatingFilterType(val nameParams: String){
    GLOBAL("global"),
    COUNTRY("country"),
    CITY("city")
}