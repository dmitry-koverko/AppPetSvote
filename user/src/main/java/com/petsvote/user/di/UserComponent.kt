package com.petsvote.user.di

import android.app.Application
import android.content.Context
import com.petsvote.domain.usecases.configuration.*
import com.petsvote.domain.usecases.user.IGetUserPetsUseCase
import com.petsvote.domain.usecases.user.IGetUserUseCase
import com.petsvote.user.UserProfileFragment
import com.petsvote.user.crop.CropUserImageFragment
import com.petsvote.user.settings.SettingProfileFragment
import com.petsvote.user.simple.SimpleUserFragment
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
    fun injectUserProfile(userProfileFragment: UserProfileFragment)
    fun injectCrop(cropUserImageFragment: CropUserImageFragment)

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
    val getAddPhotosSettingsUseCase: IGetAddPhotosSettingsUseCase
    val setAddPhotosSettingsUseCase: ISetAddPhotosSettingsUseCase
    val getImagesUseCase: IGetImagesUseCase
    val setImageUseCase: ISetImageUseCase
    val setImageCropUseCase: ISetImageCropUseCase
}

val Context.userDepsProvider: UserDepsProvider
    get() = when(this){
        is UserDepsProvider -> this
        is Application -> error("Application must imolements AuthorizationDepsProvider")
        else -> applicationContext.userDepsProvider
    }