package com.petsvote.room.dao

import androidx.room.*
import com.petsvote.room.entity.EntityUserProfile
import com.petsvote.room.entity.user.EntityUserInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(image: EntityUserProfile)

    @Update
    suspend fun update(userInfo: EntityUserProfile)

    @Query("select * from EntityUserProfile")
    fun getImage(): EntityUserProfile?

    @Query("select * from EntityUserProfile")
    fun getImageFlow(): Flow<EntityUserProfile?>

    @Query("select * from EntityUserProfile")
    fun getUserProfile(): EntityUserProfile?

    @Query("UPDATE EntityUserProfile SET image =:bytes")
    suspend fun updateImage(bytes: ByteArray?)

    @Query("UPDATE EntityUserProfile SET imageCrop =:bytes")
    suspend fun updateImageCrop(bytes: ByteArray?)

    @Query("UPDATE EntityUserProfile SET locationCountryId =:id, locationCountryTitle =:title,locationCityId =-1")
    suspend fun updateLocationCountryId(id: Int, title: String)

    @Query("UPDATE EntityUserProfile SET locationCityId =:id, locationCityTitle =:title, locationRegion =:titleRegion")
    suspend fun updateLocationCityId(id: Int, title: String, titleRegion: String)
}