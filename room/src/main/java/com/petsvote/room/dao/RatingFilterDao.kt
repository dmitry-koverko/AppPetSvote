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

    @Query("UPDATE entityratingfilter SET type =:type, sex=:sex, city_id=:citiId, country_id=:countryId, age_between_min =:age_between_min, age_between_max=:age_between_max, id=:id, breed_id=:breed_id")
    suspend fun reset(type: String?, sex: String?, citiId: Int?, countryId: Int?, age_between_max: Int, age_between_min: Int, id: Int?, breed_id: Int?)

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