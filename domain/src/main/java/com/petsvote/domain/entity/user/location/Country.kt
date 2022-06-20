package com.petsvote.domain.entity.user.location

import com.petsvote.core.adapter.Item

data class Country(
    val id: Int,
    val title: String,
    var isSelect: Boolean = false
): Item