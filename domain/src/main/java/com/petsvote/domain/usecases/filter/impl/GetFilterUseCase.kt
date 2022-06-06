package com.petsvote.domain.usecases.filter.impl

import com.petsvote.domain.entity.filter.Filter
import com.petsvote.domain.repository.IResourcesRepository
import com.petsvote.domain.repository.rating.IRatingFilterRepository
import com.petsvote.domain.usecases.filter.IGetFilterUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFilterUseCase @Inject constructor(
    private val resourcesRepository: IResourcesRepository,
    private val ratingFilterRepository: IRatingFilterRepository
) : IGetFilterUseCase {

    var scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override suspend fun getFilter(): Flow<Filter> = flow {
        var filter = Filter()
        ratingFilterRepository.getRatingFilter().collect {
            filter.sex = when (it.sex) {
                "MALE" -> 1
                "FEMALE" -> 2
                else -> 0
            }

            filter.kind =
                if (it.type == null)
                    resourcesRepository.getStringUiByName("all_kinds")
                else if(it.type.split(",").size < 2) resourcesRepository.getStringByName(it.type)
                else resourcesRepository.getStringUiByName("n_kinds")

            emit(filter)
        }

    }
}