package com.petsvote.register.di

import android.app.Application
import android.content.Context
import com.petsvote.domain.usecases.rating.ISetDefaultRatingFilterUseCase
import com.petsvote.domain.usecases.user.IRegisterUserUseCase
import com.petsvote.domain.usecases.user.ISaveUserToLocalUseCase
import com.petsvote.register.RegisterFragment
import dagger.Component
import dagger.Module
import javax.inject.Scope

@Scope
internal annotation class RegisterScope

@[RegisterScope Component(
    dependencies = [RegisterDeps::class],
    modules = [RegisterModule::class])]
interface RegisterComponent {

    fun inject(registerFragment: RegisterFragment)

    @Component.Builder
    interface Builder{

        fun deps(registerDeps: RegisterDeps): Builder
        fun build(): RegisterComponent

    }

}

@Module
internal class RegisterModule{

}

interface RegisterDepsProvider {

    var depsRegister: RegisterDeps

}

interface RegisterDeps{
    val registerUserUseCase: IRegisterUserUseCase
    val saveUserUseCase: ISaveUserToLocalUseCase
    val setDefaultRatingFilterUseCase: ISetDefaultRatingFilterUseCase
}

val Context.registerDepsProvider: RegisterDepsProvider
    get() = when(this){
        is RegisterDepsProvider -> this
        is Application -> error("Application must imolements AuthorizationDepsProvider")
        else -> applicationContext.registerDepsProvider
    }