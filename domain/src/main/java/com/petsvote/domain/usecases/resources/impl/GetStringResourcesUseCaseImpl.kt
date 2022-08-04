package com.petsvote.domain.usecases.resources.impl

import android.content.res.Resources
import com.petsvote.domain.repository.IResourcesRepository
import com.petsvote.domain.usecases.resources.GetStringResourcesUseCase
import javax.inject.Inject

class GetStringResourcesUseCaseImpl @Inject constructor(
    private val resources: IResourcesRepository
): GetStringResourcesUseCase {

    override fun getString(id: Int, vararg inp: String): String {
        return resources.getString(id, input = inp)
    }

    override fun getString(name: String, vararg inp: String): String {
        return resources.getStringUiByName(name, input = inp)
    }
}