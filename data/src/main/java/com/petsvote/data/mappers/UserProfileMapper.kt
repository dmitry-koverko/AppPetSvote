package com.petsvote.data.mappers

import com.petsvote.domain.entity.configuration.UserProfile
import com.petsvote.room.entity.EntityBreed
import com.petsvote.room.entity.EntityUserProfile

fun EntityUserProfile.toUserProfile(): UserProfile {
    return UserProfile(
        type = this.type,
        image = this.image,
        imageCrop = this.imageCrop,
        locationCountryTitle = this.locationCountryTitle,
        locationCountryId = this.locationCountryId,
        locationCityTitle = this.locationCityTitle,
        locationCityId = this.locationCityId
    )
}