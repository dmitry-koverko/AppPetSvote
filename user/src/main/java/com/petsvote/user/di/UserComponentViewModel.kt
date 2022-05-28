package com.petsvote.user.di

import android.app.Application
import androidx.lifecycle.AndroidViewModel

internal class UserComponentViewModel(application: Application): AndroidViewModel(application) {

    val userComponent: UserComponent by lazy {
        DaggerUserComponent.builder()
            .deps(application.userDepsProvider.depsUser)
            .build()
    }

}