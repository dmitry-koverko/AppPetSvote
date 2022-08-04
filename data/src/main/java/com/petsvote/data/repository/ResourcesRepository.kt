package com.petsvote.data.repository

import android.content.Context
import android.content.res.Resources
import com.petsvote.domain.repository.IResourcesRepository
import javax.inject.Inject

class ResourcesRepository @Inject constructor(
    private val resources: Resources,
    private val context: Context
): IResourcesRepository {

    override fun getString(resourcesId: Int, vararg input: String): String {
        return resources.getString(resourcesId, input)
    }

    override fun getStringByName(string: String, vararg input: String): String {
        var  id =  resources.getIdentifier(string, "string", context.packageName)
        return if(id != 0) resources.getString(id, input) else ""
    }

    override fun getStringUiByName(string: String,vararg input: String): String {
        var  id =  resources.getIdentifier(string, "string", context.packageName)
        return if(id != 0) resources.getString(id, input) else ""
    }
}