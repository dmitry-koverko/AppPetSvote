package com.petswote.pet.di

import android.app.Application
import android.content.Context
import com.petsvote.domain.usecases.configuration.*
import com.petsvote.domain.usecases.filter.IGetKindsUseCase
import com.petsvote.domain.usecases.filter.ISetBreedUseCase
import com.petsvote.domain.usecases.pet.IFindPetUseCase
import com.petsvote.domain.usecases.pet.IGetBreedByIdUseCase
import com.petsvote.domain.usecases.pet.IGetPetDetailsUseCase
import com.petsvote.domain.usecases.pet.add.IAddPetUseCase
import com.petsvote.domain.usecases.pet.add.IEditPetUseCase
import com.petsvote.domain.usecases.pet.create.*
import com.petsvote.domain.usecases.user.*
import com.petswote.pet.add.AddPetFragment
import com.petswote.pet.breeds.PetSelectBreedsFragment
import com.petswote.pet.crop.CropPetImageFragment
import com.petswote.pet.edit.EditPetFragment
import com.petswote.pet.find.FindPetFragment
import com.petswote.pet.info.PetInfoFragment
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
    fun injectFindPet(findPetFragment: FindPetFragment)
    fun injectPetInfo(petInfoFragment: PetInfoFragment)
    fun injectEdit(editPetFragment: EditPetFragment)

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
    val findPetUseCase: IFindPetUseCase
    val petDetailsUseCase: IGetPetDetailsUseCase
    val getBreedByIdUseCase: IGetBreedByIdUseCase
    val addPetUseCase: IAddPetUseCase
    val setBreedsUseCase: ISetBreedUseCase
    val editPetUseCase: IEditPetUseCase

}

val Context.petDepsProvider: PetDepsProvider
    get() = when(this){
        is PetDepsProvider -> this
        is Application -> error("Application must imolements AuthorizationDepsProvider")
        else -> applicationContext.petDepsProvider
    }