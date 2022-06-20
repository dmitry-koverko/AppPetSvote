package com.petsvote.data.mappers

import com.petsvote.retrofit.entity.user.location.Country

fun List<Country>.toCountry(): List<com.petsvote.domain.entity.user.location.Country>{
    var list = mutableListOf<com.petsvote.domain.entity.user.location.Country>()
    for (country in this){
        list.add(
            com.petsvote.domain.entity.user.location.Country(
                country.id,
                country.title,
                false
            )
        )
    }
    return list
}