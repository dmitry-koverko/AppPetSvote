package com.petsvote.domain.repository

import android.graphics.Bitmap
import com.petsvote.domain.entity.filter.Kind
import com.petsvote.domain.entity.pet.FindPet
import com.petsvote.domain.entity.pet.Pet
import com.petsvote.domain.entity.pet.PetDetails
import com.petsvote.domain.entity.pet.PetPhoto
import kotlinx.coroutines.flow.Flow

interface IPetRepository {

    suspend fun insertEmptyPetProfile()
    suspend fun addPetPhoto(byteArray: ByteArray)
    suspend fun setImage(byteArray: ByteArray)
    suspend fun getImage(): Flow<ByteArray>
    suspend fun getImagesCrop(): Flow<List<PetPhoto>>
    suspend fun removeImagePet(id: Int)
    suspend fun setPetName(name: String)
    suspend fun getSelectKindId(): Int?
    suspend fun setSelectKind(id: Int, title: String)
    suspend fun setSelectBreed(id: Int, title: String)
    suspend fun getSelectBreed(): String
    suspend fun setSelectBirthday(date: String)
    suspend fun setSelectInsta(insta: String)
    suspend fun setSelectSex(sex: Int)
    suspend fun findPet(petId: Int): FindPet?
    suspend fun petDetails(petId: Int): PetDetails?
    suspend fun addPet(list: List<Bitmap?>, kind: String): Pet?

}