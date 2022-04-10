package com.petsvote.data.mappers

import com.petsvote.retrofit.entity.configuration.Document

fun Document.toDocument(): com.petsvote.domain.entity.configuration.Document{
    return com.petsvote.domain.entity.configuration.Document(this.data)
}