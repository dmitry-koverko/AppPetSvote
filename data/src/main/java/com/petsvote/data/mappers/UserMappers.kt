package com.petsvote.data.mappers

import com.petsvote.domain.entity.user.UserInfo
import com.petsvote.retrofit.entity.user.Register
import com.petsvote.retrofit.entity.user.User
import com.petsvote.retrofit.entity.user.UserPet
import com.petsvote.room.entity.EntityPhoto
import com.petsvote.room.entity.user.EntityUserInfo
import com.petsvote.room.entity.user.EntityUserPet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.last

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

fun User.toUserInfoUC(): UserInfo {
    return UserInfo(
        id = id,
        first_name = first_name,
        has_blocked = false,
        last_name = last_name,
        avatar = avatar,
        first_vote = first_vote,
        has_paid_votes = false,
        notify_status = 0,
        official = official,
        pet = pets.remoteToUserPetList(),
        location = location?.toLocation()
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

fun com.petsvote.domain.entity.user.UserPet.toLocalUserPet(): EntityUserPet {
    return EntityUserPet(
        id = this.id,
        name = this.name,
        pets_id = this.pets_id,
        global_range = this.global_range,
        country_range = this.country_range,
        city_range = this.city_range,
        global_score = this.global_score,
        global_dynamic = this.global_dynamic,
        country_dynamic = this.country_dynamic,
        city_dynamic = this.city_dynamic,
        mark_dynamic = this.mark_dynamic,
        has_paid_votes = this.has_paid_votes,
        photos = this.photos?.toLocalPhotoList(),
    )
}

fun UserPet.toLocalUserPet(): EntityUserPet {
    return EntityUserPet(
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
        photos = this.photos?.remoteToLocalPhotoList(),
    )
}

fun List<UserPet>.remoteToLocalPetList(): List<EntityUserPet> {
    var list = mutableListOf<EntityUserPet>()
    this.onEach {
        list.add(it.toLocalUserPet())
    }
    return list
}

fun List<UserPet>.remoteToUserPetList(): List<com.petsvote.domain.entity.user.UserPet> {
    var list = mutableListOf<com.petsvote.domain.entity.user.UserPet>()
    this.onEach {
        list.add(it.remoteToUserPet())
    }
    return list
}

fun List<com.petsvote.domain.entity.user.UserPet>.toLocalUserPetList(): List<EntityUserPet> {
    var list = mutableListOf<EntityUserPet>()
    this.onEach {
        list.add(it.toLocalUserPet())
    }
    return list
}

fun com.petsvote.domain.entity.user.UserInfo.toLocalUser(bearerParams: String = ""): EntityUserInfo {
    return EntityUserInfo(
        id = this.id,
        bearer = if(bearerParams.isEmpty()) this.bearer else bearerParams,
        first_name = this.first_name,
        has_blocked = this.has_blocked,
        last_name = this.last_name,
        avatar = this.avatar,
        first_vote = this.first_vote,
        has_paid_votes = this.has_paid_votes,
        notify_status = this.notify_status,
        official = this.official,
        pet = this.pet.toLocalUserPetList(),
        location = this.location?.toLocalLocation()
    )
}

fun User.toLocalUser(bearer: String): EntityUserInfo {
    return EntityUserInfo(
        id = this.id,
        bearer = bearer,
        first_name = this.first_name,
        last_name = this.last_name,
        avatar = this.avatar,
        first_vote = this.first_vote,
        official = this.official,
        pet = this.pets.remoteToLocalPetList(),
        location = this.location?.toLocalLocation()
    )
}

fun EntityUserPet.toUserPet(): com.petsvote.domain.entity.user.UserPet{
    return com.petsvote.domain.entity.user.UserPet(
        id = this.id,
        name = this.name,
        pets_id = this.pets_id,
        global_range = this.global_range,
        country_range = this.country_range,
        city_range = this.city_range,
        global_score = this.global_score,
        global_dynamic = this.global_dynamic,
        country_dynamic = this.country_dynamic,
        city_dynamic = this.city_dynamic,
        mark_dynamic = this.mark_dynamic,
        has_paid_votes = this.has_paid_votes,
        photos = this.photos?.toPhotoList()
    )
}

fun List<EntityUserPet>.toUserPets(): List<com.petsvote.domain.entity.user.UserPet>{
    var list = mutableListOf<com.petsvote.domain.entity.user.UserPet>()
    this.onEach { list.add(it.toUserPet()) }
    return list
}

fun EntityUserInfo.toUserInfo(): UserInfo{
    return UserInfo(
        id = id,
        first_name = first_name,
        has_blocked = false,
        last_name = last_name,
        avatar = avatar,
        first_vote = first_vote,
        has_paid_votes = false,
        notify_status = 0,
        official = official,
        pet = pet.toUserPets(),
        location = location?.toLocation()
    )
}
