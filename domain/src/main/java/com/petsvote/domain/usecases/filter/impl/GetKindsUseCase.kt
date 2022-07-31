package com.petsvote.domain.usecases.filter.impl

import android.content.Context
import com.petsvote.domain.R
import com.petsvote.domain.entity.filter.Kind
import com.petsvote.domain.repository.IPetRepository
import com.petsvote.domain.repository.IResourcesRepository
import com.petsvote.domain.repository.rating.IRatingFilterRepository
import com.petsvote.domain.usecases.filter.IGetKindsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetKindsUseCase @Inject constructor(
    private val resourcesRepository: IResourcesRepository,
    private val ratingFilterRepository: IRatingFilterRepository,
    private val petRepository: IPetRepository
): IGetKindsUseCase {

    //  0 filter
    //  1 addPet
    override suspend fun getKinds(type: Int, name: String?): List<Kind> = withContext(Dispatchers.IO){
        val filter = ratingFilterRepository.getSimpleRatingFilter()
        var list = listOf<Kind>(
            Kind(0, resourcesRepository.getString(R.string.cat), "cat", 30),
            Kind(1, resourcesRepository.getString(R.string.dog), "dog", 30),
            Kind(2, resourcesRepository.getString(R.string.horse), "horse", 45),
            Kind(3, resourcesRepository.getString(R.string.ferret), "ferret", 15),
            Kind(4, resourcesRepository.getString(R.string.reptile), "reptile", 200),
            Kind(5, resourcesRepository.getString(R.string.rodent), "rodent", 20),
            Kind(6, resourcesRepository.getString(R.string.amphibian), "amphibian", 30),
            Kind(7, resourcesRepository.getString(R.string.bird), "bird", 90),
            Kind(8, resourcesRepository.getString(R.string.exotic), "exotic", 60),
            Kind(9, resourcesRepository.getString(R.string.invertebrates), "invertebrates", 100),
            Kind(10, resourcesRepository.getString(R.string.fish), "fish", 35),
            Kind(11, resourcesRepository.getString(R.string.robot), "robots", 200), //TODO update Age and id
        )

        if(type == 0) {
            var array = filter.type?.replace(" ", "")?.split(",")?.toList() ?: emptyList()
            for(kind in list){
                kind.isSelect = checkSelected(array, kind.name)
            }
        }
        else {
            if(name == null){
                var id = petRepository.getSelectKindId() ?: -1
                list.find { it.id == id }?.isSelect = true
            }else return@withContext list.filter { it.name == name }
        }

        return@withContext list
    }

    private fun checkSelected(list: List<String>, name: String): Boolean{
        if(list.isEmpty()) return true
        else return list.indexOf(name) !== -1
    }

}