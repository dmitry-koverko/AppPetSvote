package com.petsvote.domain.usecases.filter

import com.petsvote.domain.entity.filter.Kind

interface IGetKindsUseCase {

    suspend fun getKinds(type: Int, name: String? = null): List<Kind>

}