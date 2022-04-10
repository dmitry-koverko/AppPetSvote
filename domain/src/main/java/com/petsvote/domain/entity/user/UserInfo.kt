package com.petsvote.domain.entity.user

import org.jetbrains.annotations.NotNull

data class UserInfo(
    val id: Int?,
    var bearer: String? = null,
    var first_name: String? = null,
    var has_blocked: Boolean? = null,
    var last_name: String? = null,
    var avatar: String? = null,
    var first_vote: Int? = null,
    var has_paid_votes: Boolean? = null,
    var notify_status: Int? = null,
    var official: Int? = null,
    var pet: List<UserPet> = listOf(),
    var location: Location? =null,
)