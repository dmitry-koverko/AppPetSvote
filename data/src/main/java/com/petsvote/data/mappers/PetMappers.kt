package com.petsvote.data.mappers

import com.petsvote.retrofit.entity.pet.FindPet
import com.petsvote.retrofit.entity.pet.Pet
import com.petsvote.retrofit.entity.pet.PetDetails

fun FindPet.toLocalFind(): com.petsvote.domain.entity.pet.FindPet {
    return com.petsvote.domain.entity.pet.FindPet(
        pet = this.pet.toLocalPet(),
        vote = this.vote
    )
}

fun Pet.toLocalPet(): com.petsvote.domain.entity.pet.Pet {
    return com.petsvote.domain.entity.pet.Pet(
        temp_type = this.temp_type,
        id = this.id,
        create_date = this.create_date,
        pet_id = this.pet_id,
        bdate = this.bdate,
        user_id = this.user_id,
        name = this.name,
        breed_id = this.breed_id,
        sex = this.sex,
        type = this.type,
        inst = this.inst,
        count_paid_votes = this.count_paid_votes,
        last_paid_rating_sum = this.last_paid_rating_sum,
        has_paid_votes = this.has_paid_votes,
        country_name = this.country_name,
        city_name = this.city_name,
        country_id = this.country_id,
        city_id = this.city_id,
        card_type = this.card_type,
        photos = this.photos.remoteToPhotoList()
    )
}

fun PetDetails.toLocalPetDetails(): com.petsvote.domain.entity.pet.PetDetails{
    return com.petsvote.domain.entity.pet.PetDetails(
        first_name,
        last_name,
        avatar,
        official,
        global_range,
        country_range,
        city_range,
        global_score,
        global_dynamic,
        country_dynamic,
        city_dynamic,
        mark_dynamic,
        has_paid_votes
    )
}