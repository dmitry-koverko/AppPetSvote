package com.petsvote.domain.usecases.filter.impl

import android.content.Context
import com.petsvote.domain.R
import com.petsvote.domain.entity.filter.Kind
import com.petsvote.domain.repository.IResourcesRepository
import com.petsvote.domain.usecases.filter.IGetKindsUseCase
import javax.inject.Inject

class GetKindsUseCase @Inject constructor(
    val resourcesRepository: IResourcesRepository
): IGetKindsUseCase {

    override suspend fun getKinds(): List<Kind> {
        return listOf<Kind>(
            Kind(0, resourcesRepository.getString(R.string.cats), "cat", 30, true),
            Kind(1, resourcesRepository.getString(R.string.dogs), "dog", 30, true),
            Kind(2, resourcesRepository.getString(R.string.horses), "horse", 45, true),
            Kind(3, resourcesRepository.getString(R.string.ferrets), "ferret", 15, true),
            Kind(4, resourcesRepository.getString(R.string.reptiles), "reptile", 200, true),
            Kind(5, resourcesRepository.getString(R.string.rodents), "rodent", 20, true),
            Kind(6, resourcesRepository.getString(R.string.amphibians), "amphibian", 30, true),
            Kind(7, resourcesRepository.getString(R.string.birds), "bird", 90, true),
            Kind(8, resourcesRepository.getString(R.string.exotics), "exotic", 60, true),
            Kind(9, resourcesRepository.getString(R.string.invertebrates), "invertebrates", 100, true),
            Kind(10, resourcesRepository.getString(R.string.fish), "fish", 35, true),
            Kind(11, resourcesRepository.getString(R.string.robots), "rebots", 999, true),
        )
    }

}