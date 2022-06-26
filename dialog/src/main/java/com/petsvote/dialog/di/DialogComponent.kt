package com.petsvote.dialog.di

import android.app.Application
import android.content.Context
import com.petsvote.dialog.SelectPhotoDialog
import com.petsvote.domain.usecases.configuration.ISetImageUseCase
import com.petsvote.domain.usecases.pet.ISetImagePetProfileUseCase
import dagger.Component
import dagger.Module
import javax.inject.Scope

@Scope
internal annotation class DialogScope

@[DialogScope Component(
    dependencies = [DialogDeps::class],
    modules = [DialogModule::class])]

interface DialogComponent {

    fun inject(selectPhotoDialog: SelectPhotoDialog)


    @Component.Builder
    interface Builder{

        fun deps(dialogDeps: DialogDeps): Builder
        fun build(): DialogComponent

    }

}

@Module
internal class DialogModule{

}

interface DialogDepsProvider {

    var depsDialog: DialogDeps

}

interface DialogDeps{
    val setImageUseCase: ISetImageUseCase
    val setImagePetProfileUseCase: ISetImagePetProfileUseCase
}

val Context.dialogDepsProvider: DialogDepsProvider
    get() = when(this){
        is DialogDepsProvider -> this
        is Application -> error("Application must imolements AuthorizationDepsProvider")
        else -> applicationContext.dialogDepsProvider
    }