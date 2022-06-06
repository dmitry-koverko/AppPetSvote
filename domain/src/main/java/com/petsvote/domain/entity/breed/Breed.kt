package com.petsvote.domain.entity.breed

import com.petsvote.core.adapter.Item

data class Breed(
    val breedId: Int,
    val breedName: String,
    var isSelect: Boolean = false
): Item