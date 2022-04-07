package com.petsvote.data.mappers

import com.petsvote.domain.entity.user.UserInfo
import com.petsvote.retrofit.entity.user.Register

fun Register.toUserInfoUC(): UserInfo{
    return UserInfo(this.user.id)
}