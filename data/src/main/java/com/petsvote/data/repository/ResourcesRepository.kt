package com.petsvote.data.repository

import android.content.Context
import android.content.res.Resources
import com.petsvote.domain.repository.IResourcesRepository
import javax.inject.Inject

class ResourcesRepository @Inject constructor(
    private val resources: Resources,
    private val context: Context
): IResourcesRepository {

    override fun getString(resourcesId: Int): String {
        return resources.getString(resourcesId)
    }

    override fun getStringByName(string: String): String {
        var  id =  resources.getIdentifier(string, "string", context.packageName)
        return if(id != 0) resources.getString(id) else ""
    }

    override fun getStringUiByName(string: String): String {
        var  id =  resources.getIdentifier(string, "string", context.packageName)
        return if(id != 0) resources.getString(id) else ""
    }
}