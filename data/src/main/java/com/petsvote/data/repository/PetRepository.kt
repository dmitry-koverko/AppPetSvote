package com.petsvote.data.repository

import com.petsvote.data.mappers.toPetPhotoList
import com.petsvote.domain.entity.pet.PetPhoto
import com.petsvote.domain.repository.IPetRepository
import com.petsvote.domain.usecases.configuration.GetLocaleLanguageCodeUseCase
import com.petsvote.room.dao.PetProfileDao
import com.petsvote.room.dao.UserDao
import com.petsvote.room.entity.EntityPetProfile
import com.petsvote.room.entity.EnityPetImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PetRepository @Inject constructor(
    private val userDao: UserDao,
    private val profilePetDao: PetProfileDao,
    private val getLocaleLanguageCodeUseCase: GetLocaleLanguageCodeUseCase
): IPetRepository {

    override suspend fun insertEmptyPetProfile() {
        profilePetDao.clearImagesCrop()
        profilePetDao.insert(
            EntityPetProfile(1, byteArrayOf(), emptyList(), "", -1, "", -1, "",  0, 0, "")
        )
    }


    override suspend fun addPetPhoto(byteArray: ByteArray) {
        profilePetDao.addImageCrop(byteArray)
    }

    override suspend fun setImage(byteArray: ByteArray) {
        profilePetDao.updateImage(byteArray)
    }

    override suspend fun getImage(): Flow<ByteArray> = flow {
        profilePetDao.getPetProfile().collect {
            emit(it?.image ?: byteArrayOf())
        }
    }

    override suspend fun getImagesCrop(): Flow<List<PetPhoto>> = flow{
        profilePetDao.getImagesCrop().collect {
            emit(it.toPetPhotoList())
        }

    }

    override suspend fun removeImagePet(id: Int) {
        profilePetDao.removeImagePet(id)
    }


}