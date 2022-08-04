package com.petsvote.domain.di

import android.app.Application
import android.content.res.Resources
import com.petsvote.domain.repository.IResourcesRepository
import com.petsvote.domain.usecases.resources.GetStringResourcesUseCase
import com.petsvote.domain.usecases.resources.impl.GetStringResourcesUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class ResourcesModule {

    @Provides
    fun provideStringResources(resources: IResourcesRepository): GetStringResourcesUseCase {
        return GetStringResourcesUseCaseImpl(resources)
    }

}