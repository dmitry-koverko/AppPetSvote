package com.petsvote.domain.entity.filter

import kotlinx.coroutines.flow.MutableStateFlow

data class Filter(
    var kind: String = "",
    var breed: String = "",
    var ageMin: String = "",
    var ageMax: String = "",
    var sex: Int = 0,
    var isBreedRight: Boolean = false,
)