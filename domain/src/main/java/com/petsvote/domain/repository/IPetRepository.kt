package com.petsvote.domain.repository

import com.petsvote.domain.entity.pet.PetPhoto
import kotlinx.coroutines.flow.Flow

interface IPetRepository {

    suspend fun insertEmptyPetProfile()
    suspend fun addPetPhoto(byteArray: ByteArray)
    suspend fun setImage(byteArray: ByteArray)
    suspend fun getImage(): Flow<ByteArray>
    suspend fun getImagesCrop(): Flow<List<PetPhoto>>
    suspend fun removeImagePet(id: Int)

}