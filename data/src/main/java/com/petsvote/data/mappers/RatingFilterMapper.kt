package com.petsvote.data.mappers

import com.petsvote.domain.entity.filter.RatingFilter
import com.petsvote.room.entity.filter.EntityRatingFilter

fun EntityRatingFilter.toRatingFilter(): RatingFilter{
    return RatingFilter(
        type = this.type,
        sex = this.sex,
        city_id = this.city_id,
        country_id = this.country_id,
        age_between = this.age_between,
        rating_type = this.rating_type,
        id = this.id,
        breed_id = this.breed_id,
    )
}