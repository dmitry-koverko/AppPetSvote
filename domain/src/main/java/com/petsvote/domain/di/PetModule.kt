package com.petsvote.domain.di

import com.petsvote.domain.repository.IPetRepository
import com.petsvote.domain.repository.IUserRepository
import com.petsvote.domain.usecases.pet.*
import com.petsvote.domain.usecases.pet.impl.*
import com.petsvote.domain.usecases.user.impl.ISaveUserUseCase
import com.petsvote.domain.usecases.user.impl.SaveUserUseCase
import dagger.Module
import dagger.Provides

@Module
class PetModule {

    @Provides
    fun provideRemoveImageCropUseCase(petRepository: IPetRepository): IRemovePetImageUseCase {
        return RemovePetImageUseCase(petRepository)
    }

    @Provides
    fun provideGetImagesCropUseCase(petRepository: IPetRepository): IGetImagesCropUseCase {
        return GetImagesCropUseCase(petRepository)
    }

    @Provides
    fun provideAddImagePetUseCase(petRepository: IPetRepository): IAddImageCropUseCase {
        return AddImageCropUseCase(petRepository)
    }

    @Provides
    fun provideGetImagePetProfileUseCase(petRepository: IPetRepository): IGetImagePetProfileUseCase {
        return GetImagePetProfileUseCase(petRepository)
    }

    @Provides
    fun provideSetImagePetProfileUseCase(petRepository: IPetRepository): ISetImagePetProfileUseCase {
        return SetImagePetProfileUseCase(petRepository)
    }

    @Provides
    fun provideSetEmptyPetProfileUseCase(petRepository: IPetRepository): ISetEmptyPetProfileUseCase {
        return SetEmptyPetProfileUseCase(petRepository)
    }


}