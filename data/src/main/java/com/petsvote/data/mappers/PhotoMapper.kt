package com.petsvote.data.mappers

import com.petsvote.retrofit.entity.Photo
import com.petsvote.room.entity.EntityPhoto

fun Photo.remoteToPhoto(): com.petsvote.domain.entity.user.Photo {
    return com.petsvote.domain.entity.user.Photo(
        id = this.id,
        num = this.num,
        url = this.url
    )
}

fun List<Photo>.remoteToPhotoList(): List<com.petsvote.domain.entity.user.Photo> {
    var list = mutableListOf<com.petsvote.domain.entity.user.Photo>()
    this.onEach { list.add(it.remoteToPhoto()) }
    return list
}

fun com.petsvote.domain.entity.user.Photo.toLocalPhoto(): EntityPhoto {
    return EntityPhoto(
        id = this.id,
        num = this.num,
        url = this.url
    )
}

fun List<com.petsvote.domain.entity.user.Photo>.toLocalPhotoList(): List<EntityPhoto> {
    var list = mutableListOf<EntityPhoto>()
    this.onEach { list.add(it.toLocalPhoto()) }
    return list
}