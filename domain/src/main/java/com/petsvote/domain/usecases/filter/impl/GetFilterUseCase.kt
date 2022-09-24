package com.petsvote.domain.usecases.filter.impl

import android.util.Log
import com.petsvote.domain.entity.filter.Filter
import com.petsvote.domain.repository.IBreedRepository
import com.petsvote.domain.repository.IResourcesRepository
import com.petsvote.domain.repository.rating.IRatingFilterRepository
import com.petsvote.domain.usecases.filter.IGetFilterUseCase
import com.petsvote.domain.usecases.filter.IGetKindsUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.math.log

class GetFilterUseCase @Inject constructor(
    private val resourcesRepository: IResourcesRepository,
    private val ratingFilterRepository: IRatingFilterRepository,
    private val breedRepository: IBreedRepository,
    private val getKindsUseCase: IGetKindsUseCase
) : IGetFilterUseCase {

    var scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override suspend fun getFilter(): Flow<Filter> = flow {
        var filter = Filter()
        ratingFilterRepository.getRatingFilter().collect {

            filter.ageMax = "${it.age_between_max}"
            filter.ageMin = "${it.age_between_min}"

            filter.sex = when (it.sex) {
                "MALE" -> 1
                "FEMALE" -> 2
                else -> 0
            }

            filter.breed = resourcesRepository.getStringUiByName("all_breeds")

            filter.kind =
                if (it.type == null) {
                    filter.isBreedRight = false
                    filter.filterMax = 200
                    resourcesRepository.getStringUiByName("all_kinds")
                }
                else if(it.type.split(",").size == 1) {
                    breedRepository.getBreedByBreedId(it.breed_id)?.let {
                        filter.breed = it.breedName
                    }
                    filter.filterMax = getKindsUseCase.getKinds(1, it.type).get(0).age
                    filter.isBreedRight = true
                    Log.d("FilterUseCase", "filter ${getKindsUseCase.getKinds(1, it.type)}")
                    Log.d("FilterUseCase", "filter ${filter.filterMax}")
                    resourcesRepository.getStringByName(it.type)

                }
                else if(it.type.split(",").size == 2){
                    filter.isBreedRight = false
                    var arr = it.type.replace(" ", "").split(",")
                    var listMax = mutableListOf<Int>()
                    arr.onEach {
                        listMax.add(getKindsUseCase.getKinds(1, it).get(0).age)
                    }
                    filter.filterMax = listMax.maxOrNull() ?: 200
                    "${resourcesRepository.getStringByName(arr[0])}, ${resourcesRepository.getStringByName(arr[1])}"
                }
                else {
                    filter.isBreedRight = false
                    resourcesRepository.getStringUiByName("n_kinds")
                }

            emit(filter)
        }

    }
}