package com.petsvote.legal.di

import android.app.Application
import android.content.Context
import com.petsvote.domain.usecases.user.CheckLoginUserUseCase
import com.petsvote.domain.usecases.user.RegisterUserUseCase
import dagger.Component
import dagger.Module
import javax.inject.Scope

@Scope
internal annotation class TermsScope

@[TermsScope Component(
    dependencies = [TermsDeps::class],
    modules = [TermsModule::class])]

interface TermsComponent {

    //fun inject(splashFragment: SplashFragment)

    @Component.Builder
    interface Builder{

        fun deps(termsDeps: TermsDeps): Builder
        fun build(): TermsComponent

    }

}

@Module
internal class TermsModule{

}

interface TermsDepsProvider {

    var depsTerms: TermsDeps

}

interface TermsDeps{
}

val Context.termsDepsProvider: TermsDepsProvider
    get() = when(this){
        is TermsDepsProvider -> this
        is Application -> error("Application must implements Provider")
        else -> applicationContext.termsDepsProvider
    }