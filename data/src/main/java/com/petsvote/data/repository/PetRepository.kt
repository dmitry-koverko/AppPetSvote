package com.petsvote.data.repository

import com.petsvote.data.mappers.checkResult
import com.petsvote.data.mappers.toLocalFind
import com.petsvote.data.mappers.toLocalPetDetails
import com.petsvote.data.mappers.toPetPhotoList
import com.petsvote.domain.entity.filter.Kind
import com.petsvote.domain.entity.pet.PetPhoto
import com.petsvote.domain.repository.IPetRepository
import com.petsvote.domain.usecases.configuration.GetLocaleLanguageCodeUseCase
import com.petsvote.retrofit.api.PetApi
import com.petsvote.retrofit.entity.pet.FindPet
import com.petsvote.retrofit.entity.pet.PetDetails
import com.petsvote.retrofit.entity.user.User
import com.petsvote.room.dao.PetProfileDao
import com.petsvote.room.dao.UserDao
import com.petsvote.room.entity.EntityPetProfile
import com.petsvote.room.entity.EnityPetImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PetRepository @Inject constructor(
    private val petApi: PetApi,
    private val userDao: UserDao,
    private val profilePetDao: PetProfileDao,
    private val getLocaleLanguageCodeUseCase: GetLocaleLanguageCodeUseCase
) : IPetRepository {

    override suspend fun insertEmptyPetProfile() {
        profilePetDao.clearImagesCrop()
        profilePetDao.insert(EntityPetProfile(imagesCrop = emptyList(), birthday = "", sex = 0))
        profilePetDao.update(
            EntityPetProfile(1, byteArrayOf(), emptyList(), "", -1, "", -1, "", "", 0, "")
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

    override suspend fun getImagesCrop(): Flow<List<PetPhoto>> = flow {
        profilePetDao.getImagesCrop().collect {
            emit(it.toPetPhotoList())
        }

    }

    override suspend fun removeImagePet(id: Int) {
        profilePetDao.removeImagePet(id)
    }

    override suspend fun setPetName(name: String) {
        profilePetDao.updateName(name)
    }

    override suspend fun getSelectKindId(): Int? {
        return profilePetDao.getSimplePetProfile()?.kindId
    }

    override suspend fun setSelectKind(id: Int, title: String) {
        profilePetDao.updateKind(id, title)
    }

    override suspend fun setSelectBreed(id: Int, title: String) {
        profilePetDao.updateBreed(id, title)
    }

    override suspend fun getSelectBreed(): String {
        return profilePetDao.getSimplePetProfile()?.breedTitle ?: ""
    }

    override suspend fun setSelectBirthday(date: String) {
        profilePetDao.updateBirthday(date)
    }

    override suspend fun setSelectInsta(insta: String) {
        profilePetDao.updateInst(insta)
    }

    override suspend fun setSelectSex(sex: Int) {
        profilePetDao.updateSex(sex)
    }

    override suspend fun findPet(petId: Int): com.petsvote.domain.entity.pet.FindPet? {
        return checkResult<FindPet>(
            petApi.findPet(
                userDao.getToken(),
                lang = getLocaleLanguageCodeUseCase.getLanguage(),
                petId
            )
        )?.toLocalFind()
    }

    override suspend fun petDetails(petId: Int): com.petsvote.domain.entity.pet.PetDetails? {

        return checkResult<PetDetails>(
            petApi.getPetDetails(
                userDao.getToken(),
                0,//userDao.getUser().location?.city_id,
                0,//userDao.getUser().location?.country_id,
                petId,//petId,
                userDao.getUser().id,
                "0:200",
                "country",
                //"global"
            )
        )?.toLocalPetDetails()


    }
   // http://d.pvapi.site/get-pet-details?city_id=2001077&country_id=5&id=1489195&rating_type=country&user_id=3336288
    // http://d.pvapi.site/get-pet-details?city_id=0&country_id=0&id=1481559&user_id=3336319&rating_type=country&type=global

}