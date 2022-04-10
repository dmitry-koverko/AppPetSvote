package com.petsvote.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.petsvote.room.entity.user.EntityUserInfo

@Dao
interface UserDao {

    @Query("SELECT * FROM entityuserinfo")
    fun getUser(): EntityUserInfo

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userInfo: EntityUserInfo)

    @Query("DELETE FROM EntityUserInfo")
    suspend fun deleteUser()

}