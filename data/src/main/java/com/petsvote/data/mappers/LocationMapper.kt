package com.petsvote.data.mappers

import com.petsvote.retrofit.entity.user.Location
import com.petsvote.room.entity.EntityLocation

fun Location.toLocation(): com.petsvote.domain.entity.user.Location {
    return com.petsvote.domain.entity.user.Location(
        city_id = this.city_id,
        country_id = this.country_id,
        country = this.country,
        city = this.city
    )
}

fun com.petsvote.domain.entity.user.Location.toLocalLocation(): EntityLocation {
    return EntityLocation(
        city_id = this.city_id,
        country_id = this.country_id,
        country = this.country,
        city = this.city
    )
}

fun Location.toLocalLocation(): EntityLocation {
    return EntityLocation(
        city_id = this.city_id,
        country_id = this.country_id,
        country = this.country,
        city = this.city
    )
}

fun EntityLocation.toLocation(): com.petsvote.domain.entity.user.Location {
    return com.petsvote.domain.entity.user.Location(
        city_id = this.city_id,
        country_id = this.country_id,
        country = this.country,
        city = this.city
    )
}