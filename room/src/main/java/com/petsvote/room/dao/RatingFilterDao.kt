package com.petsvote.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.petsvote.room.entity.filter.EntityRatingFilter
import com.petsvote.room.entity.user.EntityUserInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface RatingFilterDao {

    @Query("SELECT * FROM entityratingfilter")
    fun getFilter(): Flow<EntityRatingFilter?>

    @Query("SELECT * FROM entityratingfilter")
    fun getSimpleFilter(): EntityRatingFilter

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ratingFilter: EntityRatingFilter)

    @Query("UPDATE entityratingfilter SET breed_id =:breedId")
    suspend fun updateBreedId(breedId: Int?)

}