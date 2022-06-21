package com.petsvote.data.mappers

import com.petsvote.retrofit.entity.user.location.City

fun List<City>.toCity(): List<com.petsvote.domain.entity.user.location.City> {
    var list = mutableListOf<com.petsvote.domain.entity.user.location.City>()
    for (city in this) {
        list.add(
            com.petsvote.domain.entity.user.location.City(
                city.id,
                city.important,
                city.country_id,
                city.title,
                city.region,
                city.area,
                city.region_id,
                false
            )
        )
    }
    return list
}