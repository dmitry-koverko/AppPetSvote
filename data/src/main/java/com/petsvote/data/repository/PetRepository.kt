package com.petsvote.data.repository

import android.content.Context
import android.graphics.Bitmap
import com.petsvote.data.mappers.*
import com.petsvote.domain.entity.pet.PetPhoto
import com.petsvote.domain.repository.IPetRepository
import com.petsvote.domain.usecases.configuration.GetLocaleLanguageCodeUseCase
import com.petsvote.domain.usecases.filter.IGetKindsUseCase
import com.petsvote.domain.usecases.filter.impl.GetKindsUseCase
import com.petsvote.retrofit.api.PetApi
import com.petsvote.retrofit.entity.pet.FindPet
import com.petsvote.retrofit.entity.pet.Pet
import com.petsvote.retrofit.entity.pet.PetDetails
import com.petsvote.retrofit.entity.user.User
import com.petsvote.room.dao.PetProfileDao
import com.petsvote.room.dao.UserDao
import com.petsvote.room.entity.EntityPetProfile
import com.petsvote.room.entity.EnityPetImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.*
import javax.inject.Inject

class PetRepository @Inject constructor(
    private val petApi: PetApi,
    private val userDao: UserDao,
    private val profilePetDao: PetProfileDao,
    private val getLocaleLanguageCodeUseCase: GetLocaleLanguageCodeUseCase,
    private val context: Context,
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

    override suspend fun addPet(list: List<Bitmap?>, kind: String): com.petsvote.domain.entity.pet.Pet? {
        var listPhotos = mutableListOf<MultipartBody.Part>()

        for (i in 0..list.size - 1) {
            if (list[i] != null) {
                listPhotos.add(buildImageBodyPart("photo_data[${i + 1}]", list[i]!!))
            }
        }

        var profilePet = profilePetDao.getSimplePetProfile()

        return checkResult<Pet>(
            petApi.addPet(
                userDao.getToken(),
                listPhotos,
                profilePet?.birthday,
                userDao.getUser().id,
                profilePet?.name,
                profilePet?.breedId.toString(),
                if(profilePet?.sex == 0) "FEMALE" else "MALE",
                kind
            )
        )?.toLocalPet()
    }

    override suspend fun editPet(list: List<Bitmap?>, kind: String, petId: Int): com.petsvote.domain.entity.pet.Pet? {
        var listPhotos = mutableListOf<MultipartBody.Part?>()
        listPhotos.add(null)
//        for (i in 0..list.size - 1) {
//            if (list[i] != null) {
//                listPhotos.add(buildImageBodyPart("photo_data[${i + 1}]", list[i]!!))
//            }
//        }

        var profilePet = profilePetDao.getSimplePetProfile()

        return checkResult<Pet>(
            petApi.editPetWithoutPhotos(
                userDao.getToken(),
                profilePet?.birthday,
                userDao.getUser().id,
                profilePet?.name,
                profilePet?.breedId.toString(),
                if(profilePet?.sex == 0) "FEMALE" else "MALE",
                kind,
                petId
            )
        )?.toLocalPet()
    }


    private fun buildImageBodyPart(fileName: String, bitmap: Bitmap): MultipartBody.Part {
        val leftImageFile = convertBitmapToFile(fileName, bitmap)
        val reqFile = RequestBody.create("image/*".toMediaTypeOrNull(), leftImageFile)
        return MultipartBody.Part.createFormData(fileName, leftImageFile.name, reqFile)
    }

    private fun convertBitmapToFile(fileName: String, bitmap: Bitmap): File {
        //create a file to write bitmap data
        val file = File(context.cacheDir, fileName)
        file.createNewFile()

        //Convert bitmap to byte array
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90 /*ignored for PNG*/, bos)
        val bitMapData = bos.toByteArray()

        //write the bytes in file
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        try {
            fos?.write(bitMapData)
            fos?.flush()
            fos?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }

}