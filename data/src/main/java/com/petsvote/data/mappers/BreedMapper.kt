package com.petsvote.data.mappers

import com.petsvote.domain.entity.pet.RatingPet
import com.petsvote.retrofit.entity.breeds.Breeds
import com.petsvote.room.entity.EntityBreed

fun List<Breeds>.remoteToLocalBreedsList(): List<EntityBreed>{
    var list = mutableListOf<EntityBreed>()
    this.onEach {
        for(breed in it.breeds){
            var breedRoom = EntityBreed(0, it.lang, it.type, breed.id,  breed.title)
            list.add(breedRoom)
        }
    }
    return list
}


