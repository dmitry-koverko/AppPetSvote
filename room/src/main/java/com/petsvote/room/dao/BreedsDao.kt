package com.petsvote.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.petsvote.room.entity.EntityBreed

@Dao
interface BreedsDao{
    @Query("SELECT * FROM EntityBreed")
    fun getBreeds(): List<EntityBreed>

    @Query("SELECT * FROM EntityBreed WHERE lang=:lang AND type=:type")
    fun getBreedsByKinds(lang: String, type: String?): List<EntityBreed>

    @Query("SELECT * FROM EntityBreed WHERE lang=:lang AND id_breed=:id")
    fun getBreedById(lang: String, id: Int): EntityBreed

    @Query("SELECT * FROM EntityBreed WHERE lang=:lang")
    fun getBreedsByLang(lang: String): List<EntityBreed>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(breeds: List<EntityBreed>)

    @Query("DELETE FROM EntityBreedList")
    suspend fun deleteAll()
}