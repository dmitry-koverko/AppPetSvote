package com.petsvote.domain.entity.user

data class SaveUserParams(
    var first_name: String,
    var last_name: String = ""
)