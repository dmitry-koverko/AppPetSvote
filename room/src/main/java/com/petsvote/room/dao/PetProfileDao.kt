package com.petsvote.room.dao

import androidx.room.*
import com.petsvote.room.entity.EntityPetProfile
import com.petsvote.room.entity.EntityUserProfile
import com.petsvote.room.entity.EnityPetImage
import kotlinx.coroutines.flow.Flow

@Dao
interface PetProfileDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(pet: EntityPetProfile)

    @Update
    suspend fun update(petInfo: EntityPetProfile)

    @Query("select * from EntityPetProfile")
    fun getPetProfile(): Flow<EntityPetProfile?>

    @Query("select * from EntityUserProfile")
    fun getUserProfile(): EntityUserProfile?

    @Query("UPDATE EntityPetProfile SET image =:bytes")
    suspend fun updateImage(bytes: ByteArray?)

    @Query("INSERT INTO EnityPetImage VALUES (null, :bytes)")
    suspend fun addImageCrop(bytes: ByteArray)

    @Query("DELETE FROM EnityPetImage")
    suspend fun clearImagesCrop()

    @Query("DELETE FROM EnityPetImage WHERE id=:id")
    suspend fun removeImagePet(id: Int)

    @Query("SELECT * from EnityPetImage")
    fun getImagesCrop(): Flow<List<EnityPetImage>>

    @Query("UPDATE EntityPetProfile SET name =:name")
    suspend fun updateName(name: String)

    @Query("UPDATE EntityPetProfile SET kindId =:id, kindTitle =:title, breedId =-1")
    suspend fun updateKind(id: Int, title: String)

    @Query("UPDATE EntityPetProfile SET breedId =:id, breedTitle =:title")
    suspend fun updateBreed(id: Int, title: String)

    @Query("UPDATE EntityPetProfile SET birthday =:birthday")
    suspend fun updateBirthday(birthday: Long)

    @Query("UPDATE EntityPetProfile SET sex =:sex")
    suspend fun updateSex(sex: Int)

    @Query("UPDATE EntityPetProfile SET inst =:inst")
    suspend fun updateInst(inst: String)

}