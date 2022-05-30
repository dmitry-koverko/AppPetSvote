package com.petsvote.domain.usecases.filter

import com.petsvote.core.adapter.Item
import com.petsvote.domain.entity.filter.Kind

interface ISetKindsRatingFilterUseCase {

    suspend fun setKinds(kinds: List<Item>)

}