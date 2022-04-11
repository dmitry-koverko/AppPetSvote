package com.petsvote.domain.usecases.resources.impl

import android.content.res.Resources
import com.petsvote.domain.usecases.resources.GetStringResourcesUseCase
import javax.inject.Inject

class GetStringResourcesUseCaseImpl @Inject constructor(
    private val resources: Resources
): GetStringResourcesUseCase {

    override fun getString(id: Int): String {
        return resources.getString(id)
    }
}