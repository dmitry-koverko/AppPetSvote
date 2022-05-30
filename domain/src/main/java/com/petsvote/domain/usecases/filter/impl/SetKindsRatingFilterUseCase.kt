package com.petsvote.domain.usecases.filter.impl

import com.petsvote.core.adapter.Item
import com.petsvote.domain.entity.filter.Kind
import com.petsvote.domain.repository.rating.IRatingFilterRepository
import com.petsvote.domain.usecases.filter.ISetKindsRatingFilterUseCase
import javax.inject.Inject

class SetKindsRatingFilterUseCase@Inject constructor(
    private val ratingFilterRepository: IRatingFilterRepository
): ISetKindsRatingFilterUseCase {

    override suspend fun setKinds(kinds: List<Item>) {

        var filterValue: String? = null
        var selectedKinds = kinds.filter { (it as Kind).isSelect }
        val listTypes: List<String> = selectedKinds.map { (it as Kind).name }
        if(kinds.isNotEmpty() && selectedKinds.size != kinds.size){
            filterValue = listTypes.joinToString()
        }

        ratingFilterRepository.setKindsRatingFilter(filterValue)
    }
}