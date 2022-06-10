package com.petsvote.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.petsvote.room.entity.filter.EntityRatingFilter
import com.petsvote.room.entity.filter.EntityRatingFilterType
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

    @Query("UPDATE entityratingfilter SET type =:kinds")
    suspend fun updateKinds(kinds: String?)

    @Query("UPDATE entityratingfilter SET sex =:sex")
    suspend fun updateSex(sex: String?)

    @Query("UPDATE entityratingfilter SET age_between_max =:maxAge")
    suspend fun updateMaxAge(maxAge: Int = 0)

    @Query("UPDATE entityratingfilter SET age_between_min =:minAge")
    suspend fun updateMinAge(minAge: Int = 0)

    @Query("UPDATE entityratingfilter SET rating_type =:type")
    suspend fun updateFilterType(type: EntityRatingFilterType)

}