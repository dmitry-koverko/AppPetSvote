package com.petsvote.data.mappers

import com.petsvote.domain.entity.pet.RatingPet
import com.petsvote.retrofit.entity.breeds.Breed
import com.petsvote.retrofit.entity.breeds.Breeds
import com.petsvote.room.entity.EntityBreed

fun List<Breeds>.remoteToLocalBreedsList(): List<EntityBreed>{
    var list = mutableListOf<EntityBreed>()
    this.onEach {
        for(breed in it.breeds){
            var breedRoom = EntityBreed(it.lang, it.type, breed.id,  breed.title)
            list.add(breedRoom)
        }
    }
    return list
}

fun List<EntityBreed>.toLocalBreedsList(): List<com.petsvote.domain.entity.breed.Breed>{
    var list = mutableListOf<com.petsvote.domain.entity.breed.Breed>()
    this.onEach {breed ->
        var breedRoom = com.petsvote.domain.entity.breed.Breed(breed.id_breed, breed.title)
        list.add(breedRoom)
    }
    return list
}

fun EntityBreed.toLocalBreed(): com.petsvote.domain.entity.breed.Breed{
    var breed = com.petsvote.domain.entity.breed.Breed(this.id_breed, this.title)
    return breed
}


