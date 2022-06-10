package com.petsvote.data.mappers

import com.petsvote.domain.entity.filter.RatingFilter
import com.petsvote.domain.entity.filter.RatingFilterType
import com.petsvote.room.entity.filter.EntityRatingFilter
import com.petsvote.room.entity.filter.EntityRatingFilterType

fun EntityRatingFilter?.toRatingFilter(): RatingFilter {
    return RatingFilter(
        type = this?.type,
        sex = this?.sex,
        city_id = this?.city_id,
        country_id = this?.country_id,
        age_between_min = this?.age_between_min,
        age_between_max = this?.age_between_max,
        rating_type = when (this?.rating_type) {
            EntityRatingFilterType.GLOBAL -> RatingFilterType.GLOBAL
            EntityRatingFilterType.COUNTRY -> RatingFilterType.COUNTRY
            EntityRatingFilterType.CITY -> RatingFilterType.CITY
            else -> RatingFilterType.GLOBAL
        },
        id = this?.id,
        breed_id = this?.breed_id,
    )
}