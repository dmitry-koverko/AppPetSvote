package com.petsvote.domain.entity.user

import com.petsvote.core.adapter.Item
import org.jetbrains.annotations.NotNull

data class UserPet(
    val id: Int?,
    val name: String?,
    var pets_id: Int?,
    val global_range: Int? = null,
    val country_range: Int? = null,
    val city_range: Int? = null,
    val global_score: Int? = null,
    val global_dynamic: Int? = null,
    val country_dynamic: Int? = null,
    val city_dynamic: Int? = null,
    val mark_dynamic: Int? = null,
    val has_paid_votes: Int? = null,
    var photos: List<Photo>? = listOf()
): Item