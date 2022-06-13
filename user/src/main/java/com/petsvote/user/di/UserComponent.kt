package com.petsvote.user.di

import android.app.Application
import android.content.Context
import com.petsvote.domain.usecases.configuration.IGetSettingsNotifyUseCase
import com.petsvote.domain.usecases.configuration.ISetSettingsNotifyUseCase
import com.petsvote.domain.usecases.user.IGetCurrentUserUseCase
import com.petsvote.domain.usecases.user.IGetUserPetsUseCase
import com.petsvote.domain.usecases.user.IGetUserUseCase
import dagger.Component
import dagger.Module
import javax.inject.Scope

@Scope
internal annotation class UserScope

@[UserScope Component(
    dependencies = [UserDeps::class],
    modules = [UserModule::class])]

interface UserComponent {

    fun inject(simpleUserFragment: SimpleUserFragment)
    fun injectSettingsProfile(settingProfileFragment: SettingProfileFragment)

    @Component.Builder
    interface Builder{

        fun deps(voteDeps: UserDeps): Builder
        fun build(): UserComponent

    }

}

@Module
internal class UserModule{

}

interface UserDepsProvider {

    var depsUser: UserDeps

}

interface UserDeps{
    val getUserPetsUseCase: IGetUserPetsUseCase
    val currentUser: IGetUserUseCase
    val settingsNotifyUseCase: IGetSettingsNotifyUseCase
    val setSettingsNotifyUseCase: ISetSettingsNotifyUseCase
}

val Context.userDepsProvider: UserDepsProvider
    get() = when(this){
        is UserDepsProvider -> this
        is Application -> error("Application must imolements AuthorizationDepsProvider")
        else -> applicationContext.userDepsProvider
    }