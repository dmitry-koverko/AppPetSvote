package com.petsvote.domain.entity.filter

import com.petsvote.core.adapter.Item

data class Kind(
    val id: Int,
    val title: String,
    val name: String,
    val age: Int,
    var isSelect: Boolean = false
): Item