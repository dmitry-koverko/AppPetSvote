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

fun List<Photo>.remoteToPhotoListString(): List<String> {
    var list = mutableListOf<String>()
    this.onEach { list.add(it.url) }
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

fun Photo.toLocalPhoto(): EntityPhoto {
    return EntityPhoto(
        id = this.id,
        num = this.num,
        url = this.url
    )
}

fun List<Photo>.remoteToLocalPhotoList(): List<EntityPhoto> {
    var list = mutableListOf<EntityPhoto>()
    this.onEach { list.add(it.toLocalPhoto()) }
    return list
}

fun EntityPhoto.toPhoto(): com.petsvote.domain.entity.user.Photo{
    return com.petsvote.domain.entity.user.Photo(
        id = this.id,
        num = this.num,
        url = this.url
    )
}

fun List<EntityPhoto>.toPhotoList(): List<com.petsvote.domain.entity.user.Photo> {
    var list = mutableListOf<com.petsvote.domain.entity.user.Photo>()
    this.onEach { list.add(it.toPhoto()) }
    return list
}