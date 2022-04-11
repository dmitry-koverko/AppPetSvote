package com.petsvote.ui.di

import android.app.Application
import android.content.res.Resources
import dagger.Module
import dagger.Provides

@Module
class UIModule {

    @Provides
    fun provideResources(application: Application): Resources{
        return application.baseContext.resources
    }
}