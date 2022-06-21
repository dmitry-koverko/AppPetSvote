package com.petsvote.user.di

import android.app.Application
import android.content.Context
import com.petsvote.domain.usecases.configuration.*
import com.petsvote.domain.usecases.user.*
import com.petsvote.domain.usecases.user.impl.ISaveUserUseCase
import com.petsvote.user.UserProfileFragment
import com.petsvote.user.crop.CropUserImageFragment
import com.petsvote.user.select.SelectCityFragment
import com.petsvote.user.select.SelectCountryFragment
import com.petsvote.user.select.SelectCountryViewModel
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
    fun injectSelectCountry(selectCountryFragment: SelectCountryFragment)
    fun injectSelectCity(selectCityFragment: SelectCityFragment)

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
    val getCountryListUseCase: IGetCountryListUseCase
    val setCountryUseCase: ISetCountryUseCase
    val getCitiesListUseCase: IGetCitiesListUseCase
    val setCityUseCase: ISetCityUseCase
    val saveUserProfileUseCase: ISaveUserUseCase
}

val Context.userDepsProvider: UserDepsProvider
    get() = when(this){
        is UserDepsProvider -> this
        is Application -> error("Application must imolements AuthorizationDepsProvider")
        else -> applicationContext.userDepsProvider
    }