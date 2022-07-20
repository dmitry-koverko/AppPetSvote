package com.petsvote.domain.di

import com.petsvote.domain.repository.IBreedRepository
import com.petsvote.domain.repository.IPetRepository
import com.petsvote.domain.repository.IUserRepository
import com.petsvote.domain.repository.breeds.IPetBreedsPagingRepository
import com.petsvote.domain.usecases.filter.impl.GetKindsUseCase
import com.petsvote.domain.usecases.pet.*
import com.petsvote.domain.usecases.pet.create.*
import com.petsvote.domain.usecases.pet.create.impl.*
import com.petsvote.domain.usecases.pet.impl.*
import com.petsvote.domain.usecases.user.IRegisterUserUseCase
import dagger.Module
import dagger.Provides

@Module
class PetModule {

    @Provides
    fun provideIGetBreedByIdUseCase(breedRepository: IBreedRepository, userRepository: IUserRepository): IGetBreedByIdUseCase {
        return GetBreedByIdUseCase(breedRepository, userRepository)
    }


    @Provides
    fun provideGetPetDetailsUseCase(petRepository: IPetRepository): IGetPetDetailsUseCase {
        return GetPetDetailsUseCase(petRepository = petRepository)
    }


    @Provides
    fun provideFindPetUseCase(petRepository: IPetRepository): IFindPetUseCase {
        return FindPetUseCase(petRepository = petRepository)
    }


    @Provides
    fun provideGetInstagramUserNameUseCase(userRepository: IUserRepository): IGetInstagramUserNameUseCase {
        return GetInstagramUserNameUseCase(userRepository = userRepository)
    }


    @Provides
    fun provideSetSexPetUseCase(petRepository: IPetRepository): ISetSexPetUseCase {
        return SetSexPetUseCase(petRepository)
    }

    @Provides
    fun provideSetBirthdayPetUseCase(petRepository: IPetRepository): ISetBirthdayPetUseCase {
        return SetBirthdayPetUseCase(petRepository)
    }

    @Provides
    fun provideGetBreedPetUseCase(petRepository: IPetRepository): IGetBreedPetUseCase {
        return GetBreedPetUseCase(petRepository)
    }

    @Provides
    fun provideSetPetBreedUseCase(petRepository: IPetRepository): ISetPetBreedUseCase {
        return SetPetBreedUseCase(petRepository)
    }

    @Provides
    fun providePetGetBreedsPagingUseCase(
        petRepository: IPetRepository,
        breedsPagingRepository: IPetBreedsPagingRepository,
        getKindsUseCase: GetKindsUseCase
    ): IPetGetBreedsPagingUseCase {
        return PetGetBreedsPagingUseCase(
            breedsPagingRepository = breedsPagingRepository,
            petRepository = petRepository,
            getKindsUseCase = getKindsUseCase
        )
    }

    @Provides
    fun provideGetKindPetUseCase(
        petRepository: IPetRepository,
        kindsUseCase: GetKindsUseCase
    ): IGetKindPetUseCase {
        return GetKindPetUseCase(petRepository, getKindsUseCase = kindsUseCase)
    }

    @Provides
    fun provideSetKindPetUseCase(petRepository: IPetRepository): ISetKindPetUseCase {
        return SetKindPetUseCase(petRepository)
    }

    @Provides
    fun provideSetPetNameUseCase(petRepository: IPetRepository): ISetPetNameUseCase {
        return SetPetNameUseCase(petRepository)
    }

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