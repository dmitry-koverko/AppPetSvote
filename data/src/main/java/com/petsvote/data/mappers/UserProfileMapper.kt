package com.petsvote.data.mappers

import com.petsvote.domain.entity.configuration.UserProfile
import com.petsvote.room.entity.EntityBreed
import com.petsvote.room.entity.EntityUserProfile

fun EntityUserProfile.toUserProfile(): UserProfile {
    return UserProfile(
        type = checkNotNull(this.type),
        image = this.image,
        imageCrop = this.imageCrop,
        locationCountryTitle = checkNotNull(this.locationCountryTitle),
        locationCountryId = checkNotNull(this.locationCountryId),
        locationCityTitle = checkNotNull(this.locationCityTitle),
        locationCityId = checkNotNull(this.locationCityId)
    )
}