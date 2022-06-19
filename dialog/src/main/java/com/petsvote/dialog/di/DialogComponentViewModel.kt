package com.petsvote.dialog.di

import android.app.Application
import androidx.lifecycle.AndroidViewModel

internal class DialogComponentViewModel(application: Application): AndroidViewModel(application) {

    val dialogComponent: DialogComponent by lazy {
        DaggerDialogComponent.builder()
            .deps(application.dialogDepsProvider.depsDialog)
            .build()
    }

}