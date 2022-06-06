package com.petsvote.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.petsvote.room.entity.EntityBreed

@Dao
interface BreedsDao {
    @Query("SELECT * FROM EntityBreed")
    fun getBreeds(): List<EntityBreed>

    @Query("SELECT * FROM EntityBreed WHERE title LIKE '%' || :text || '%' AND lang=:lang AND type=:type ORDER BY id_breed ASC LIMIT :limit OFFSET :offset ")
    fun getBreedsByKinds(lang: String, type: String?, offset: Int, text: String, limit: Int): List<EntityBreed>

    @Query("SELECT * FROM EntityBreed WHERE lang=:lang AND id_breed=:id")
    fun getBreedById(lang: String, id: Int): EntityBreed

    @Query("SELECT * FROM EntityBreed WHERE lang=:lang")
    fun getBreedsByLang(lang: String): List<EntityBreed>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(breeds: List<EntityBreed>)

    @Query("DELETE FROM EntityBreed")
    suspend fun deleteAll()
}

//AND id_breed != -1 AND id_breed != -2