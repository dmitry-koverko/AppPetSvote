package com.petsvote.domain.flow

import com.petsvote.domain.entity.pet.VotePet
import kotlinx.coroutines.flow.MutableStateFlow

var ratingUpdate = MutableStateFlow<Boolean?>(true)