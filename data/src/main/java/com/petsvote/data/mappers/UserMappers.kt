package com.petsvote.data.mappers

import com.petsvote.domain.entity.user.UserInfo
import com.petsvote.retrofit.entity.user.Register
import com.petsvote.retrofit.entity.user.UserPet
import com.petsvote.room.entity.user.EntityUserInfo

fun Register.toUserInfoUC(): UserInfo {
    return UserInfo(
        id = this.user.id,
        bearer = this.bearer,
        first_name = this.user.first_name,
        has_blocked = false,
        last_name = this.user.last_name,
        avatar = this.user.avatar,
        first_vote = this.user.first_vote,
        has_paid_votes = false,
        notify_status = 0,
        official = this.user.official,
        pet = this.user.pets.remoteToUserPetList()
    )
}

fun UserPet.remoteToUserPet(): com.petsvote.domain.entity.user.UserPet {
    return com.petsvote.domain.entity.user.UserPet(
        id = this.id,
        name = this.name,
        pets_id = this.pet_id,
        global_range = this.global_range,
        country_range = this.country_range,
        city_range = this.city_range,
        global_score = this.global_score,
        global_dynamic = this.global_dynamic,
        country_dynamic = this.country_dynamic,
        city_dynamic = this.city_dynamic,
        mark_dynamic = this.mark_dynamic,
        has_paid_votes = this.has_paid_votes,
        photos = this.photos?.remoteToPhotoList()
    )
}

fun List<UserPet>.remoteToUserPetList(): List<com.petsvote.domain.entity.user.UserPet> {
    var list = mutableListOf<com.petsvote.domain.entity.user.UserPet>()
    this.onEach {
        list.add(it.remoteToUserPet())
    }
    return list
}

fun UserInfo.toLocalUser(): EntityUserInfo {
    return EntityUserInfo(

    )
}