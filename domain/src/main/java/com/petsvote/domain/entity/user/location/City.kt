package com.petsvote.domain.entity.user.location

import com.petsvote.core.adapter.Item
import kotlinx.serialization.Serializable

data class City(
    val id: Int,
    val important: Int?,
    val country_id: Int,
    val title: String,
    val region: String?,
    val area: String?,
    val region_id: Int?,
    var isSelect: Boolean = false
): Item