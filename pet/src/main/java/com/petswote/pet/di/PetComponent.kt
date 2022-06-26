package com.petswote.pet.di

import android.app.Application
import android.content.Context
import com.petsvote.domain.usecases.configuration.*
import com.petsvote.domain.usecases.pet.IAddImageCropUseCase
import com.petsvote.domain.usecases.pet.IGetImagePetProfileUseCase
import com.petsvote.domain.usecases.pet.IGetImagesCropUseCase
import com.petsvote.domain.usecases.pet.IRemovePetImageUseCase
import com.petsvote.domain.usecases.user.*
import com.petsvote.domain.usecases.user.impl.ISaveUserUseCase
import com.petswote.pet.add.AddPetFragment
import com.petswote.pet.crop.CropPetImageFragment
import dagger.Component
import dagger.Module
import javax.inject.Scope

@Scope
internal annotation class PetScope

@[PetScope Component(
    dependencies = [PetDeps::class],
    modules = [PetModule::class])]

interface PetComponent {

    fun inject(addPetFragment: AddPetFragment)
    fun injectCrop(cropPetImageFragment: CropPetImageFragment)

    @Component.Builder
    interface Builder{

        fun deps(petDeps: PetDeps): Builder
        fun build(): PetComponent

    }

}

@Module
internal class PetModule{

}

interface PetDepsProvider {

    var depsPet: PetDeps

}

interface PetDeps{
    val getUserPetsUseCase: IGetUserPetsUseCase
    val currentUser: IGetUserUseCase
    val getImagesUseCase: IGetImagesUseCase
    val setImageUseCase: ISetImageUseCase
    val setImageCropUseCase: ISetImageCropUseCase
    val addImageCropUseCase: IAddImageCropUseCase
    val getImagePetProfileUseCase: IGetImagePetProfileUseCase
    val getImagesCropUseCase: IGetImagesCropUseCase
    val getRemoveImageUseCase: IRemovePetImageUseCase
}

val Context.petDepsProvider: PetDepsProvider
    get() = when(this){
        is PetDepsProvider -> this
        is Application -> error("Application must imolements AuthorizationDepsProvider")
        else -> applicationContext.petDepsProvider
    }