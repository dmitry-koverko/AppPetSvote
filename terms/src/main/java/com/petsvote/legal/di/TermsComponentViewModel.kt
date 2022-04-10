package com.petsvote.legal.di

import android.app.Application
import androidx.lifecycle.AndroidViewModel

internal class TermsComponentViewModel(application: Application): AndroidViewModel(application) {

    val termsComponent: TermsComponent by lazy {
        DaggerTermsComponent.builder()
            .deps(application.termsDepsProvider.depsTerms)
            .build()
    }

}