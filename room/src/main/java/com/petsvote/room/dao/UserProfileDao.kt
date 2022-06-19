package com.petsvote.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.petsvote.room.entity.EntityUserProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(image: EntityUserProfile)

    @Query("select * from EntityUserProfile")
    fun getImage(): EntityUserProfile?

    @Query("select * from EntityUserProfile")
    fun getImageFlow(): Flow<EntityUserProfile?>

    @Query("UPDATE EntityUserProfile SET image =:bytes")
    suspend fun updateImage(bytes: ByteArray?)

    @Query("UPDATE EntityUserProfile SET imageCrop =:bytes")
    suspend fun updateImageCrop(bytes: ByteArray?)
}