package com.petsvote.domain.usecases.filter.impl

import android.content.Context
import com.petsvote.domain.R
import com.petsvote.domain.entity.filter.Kind
import com.petsvote.domain.repository.IResourcesRepository
import com.petsvote.domain.repository.rating.IRatingFilterRepository
import com.petsvote.domain.usecases.filter.IGetKindsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetKindsUseCase @Inject constructor(
    private val resourcesRepository: IResourcesRepository,
    private val ratingFilterRepository: IRatingFilterRepository
): IGetKindsUseCase {

    override suspend fun getKinds(): List<Kind> = withContext(Dispatchers.IO){
        val filter = ratingFilterRepository.getSimpleRatingFilter()

        var array = filter.type?.replace(" ", "")?.split(",")?.toList() ?: emptyList()

        return@withContext listOf<Kind>(
            Kind(0, resourcesRepository.getString(R.string.cat), "cat", 30, checkSelected(array, "cat")),
            Kind(1, resourcesRepository.getString(R.string.dog), "dog", 30, checkSelected(array, "dog")),
            Kind(2, resourcesRepository.getString(R.string.horse), "horse", 45, checkSelected(array, "horse")),
            Kind(3, resourcesRepository.getString(R.string.ferret), "ferret", 15, checkSelected(array, "ferret")),
            Kind(4, resourcesRepository.getString(R.string.reptile), "reptile", 200, checkSelected(array, "reptile")),
            Kind(5, resourcesRepository.getString(R.string.rodent), "rodent", 20, checkSelected(array, "rodent")),
            Kind(6, resourcesRepository.getString(R.string.amphibian), "amphibian", 30, checkSelected(array, "amphibian")),
            Kind(7, resourcesRepository.getString(R.string.bird), "bird", 90, checkSelected(array, "bird")),
            Kind(8, resourcesRepository.getString(R.string.exotic), "exotic", 60, checkSelected(array, "exotic")),
            Kind(9, resourcesRepository.getString(R.string.invertebrates), "invertebrates", 100, checkSelected(array, "invertebrates")),
            Kind(10, resourcesRepository.getString(R.string.fish), "fish", 35, checkSelected(array, "fish")),
            Kind(11, resourcesRepository.getString(R.string.robot), "robots", 999, checkSelected(array, "robots")), //TODO update Age and id
        )
    }

    private fun checkSelected(list: List<String>, name: String): Boolean{
        if(list.isEmpty()) return true
        else return list.indexOf(name) !== -1
    }

}