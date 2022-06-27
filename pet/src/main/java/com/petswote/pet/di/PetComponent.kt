package com.petswote.pet.di

import android.app.Application
import android.content.Context
import com.petsvote.domain.usecases.configuration.*
import com.petsvote.domain.usecases.filter.IGetKindsUseCase
import com.petsvote.domain.usecases.pet.create.*
import com.petsvote.domain.usecases.user.*
import com.petswote.pet.add.AddPetFragment
import com.petswote.pet.breeds.PetSelectBreedsFragment
import com.petswote.pet.crop.CropPetImageFragment
import com.petswote.pet.kinds.PetSelectKindsFragment
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
    fun injectSelectKinds(petSelectKindsFragment: PetSelectKindsFragment)
    fun injectSelectBreeds(petSelectBreedsFragment: PetSelectBreedsFragment)

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
    val getAddPhotosSettingsUseCase: IGetAddPhotosSettingsUseCase
    val setAddPhotosSettingsUseCase: ISetAddPhotosSettingsUseCase
    val setNamePetUseCase: ISetPetNameUseCase
    val kindsUseCase: IGetKindsUseCase
    val setKindPetUseCase: ISetKindPetUseCase
    val getKindPetUseCase: IGetKindPetUseCase
    val petGetBreedsPagingUseCase: IPetGetBreedsPagingUseCase
    val setPetBreedUseCase: ISetPetBreedUseCase
    val getBreedPetUseCase: IGetBreedPetUseCase
    val setBirthdayPetUseCase: ISetBirthdayPetUseCase
    val setSexPetUseCase: ISetSexPetUseCase
    val getInstagramUserNameUseCase: IGetInstagramUserNameUseCase
}

val Context.petDepsProvider: PetDepsProvider
    get() = when(this){
        is PetDepsProvider -> this
        is Application -> error("Application must imolements AuthorizationDepsProvider")
        else -> applicationContext.petDepsProvider
    }