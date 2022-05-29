package com.petsvote.domain.di

import android.content.res.Resources
import com.petsvote.domain.repository.IResourcesRepository
import com.petsvote.domain.usecases.filter.IGetKindsUseCase
import com.petsvote.domain.usecases.filter.impl.GetKindsUseCase
import com.petsvote.domain.usecases.resources.GetStringResourcesUseCase
import com.petsvote.domain.usecases.resources.impl.GetStringResourcesUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class FilterModule {

    @Provides
    fun provideKinds(resourcesRepository: IResourcesRepository): IGetKindsUseCase {
        return GetKindsUseCase(resourcesRepository = resourcesRepository)
    }

}