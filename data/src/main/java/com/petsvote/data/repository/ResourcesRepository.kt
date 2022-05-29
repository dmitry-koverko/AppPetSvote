package com.petsvote.data.repository

import android.content.res.Resources
import com.petsvote.domain.repository.IResourcesRepository
import javax.inject.Inject

class ResourcesRepository @Inject constructor(
    val resources: Resources
): IResourcesRepository {

    override fun getString(resourcesId: Int): String {
        return resources.getString(resourcesId)
    }
}