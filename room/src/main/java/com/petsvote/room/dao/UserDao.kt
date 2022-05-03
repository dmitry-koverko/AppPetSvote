package com.petsvote.room.dao

import androidx.room.*
import com.petsvote.room.entity.user.EntityUserInfo
import com.petsvote.room.entity.user.EntityUserPet
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM entityuserinfo")
    fun getUser(): EntityUserInfo

    @Query("SELECT bearer FROM entityuserinfo")
    suspend fun getToken(): String

    @Query("SELECT * FROM entityuserinfo")
    fun getUserFlow(): Flow<EntityUserInfo>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userInfo: EntityUserInfo)

    @Update
    suspend fun update(userInfo: EntityUserInfo)

    @Query("DELETE FROM EntityUserInfo")
    suspend fun deleteUser()

}