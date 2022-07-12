package com.petsvote.data.repository

import com.petsvote.data.mappers.toRatingFilter
import com.petsvote.domain.entity.filter.RatingFilter
import com.petsvote.domain.entity.filter.RatingFilterType
import com.petsvote.domain.repository.rating.IRatingFilterRepository
import com.petsvote.room.dao.RatingFilterDao
import com.petsvote.room.entity.filter.EntityRatingFilter
import com.petsvote.room.entity.filter.EntityRatingFilterType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RatingFilterRepository @Inject constructor(
    private val ratingFilterDao: RatingFilterDao
): IRatingFilterRepository {

    override suspend fun getRatingFilter(): Flow<RatingFilter> = flow {
        run {
            ratingFilterDao.getFilter().collect {
                val filter = it?.toRatingFilter()
                filter?.let { it1 -> emit(it1) }
            }
        }
    }

    override suspend fun getSimpleRatingFilter(): RatingFilter {
        return ratingFilterDao.getSimpleFilter().toRatingFilter()
    }

    override suspend fun setBredIdRatingFilter(breedId: Int?) {
        ratingFilterDao.updateBreedId(breedId = breedId)
    }

    override suspend fun setKindsRatingFilter(kinds: String?) {
        ratingFilterDao.updateKinds(kinds)
    }


    override suspend fun setDefaultRatingFilter() {
        ratingFilterDao.reset(
            type = null,
            sex = null,
            citiId = null,
            countryId = null,
            age_between_max = 30,
            age_between_min = 0,
            id = null,
            breed_id = null)
    }

    override suspend fun setRatingFilterType(typeRating: RatingFilterType) {
        ratingFilterDao.updateFilterType(
            when(typeRating){
                RatingFilterType.GLOBAL -> EntityRatingFilterType.GLOBAL
                RatingFilterType.COUNTRY -> EntityRatingFilterType.COUNTRY
                RatingFilterType.CITY -> EntityRatingFilterType.CITY
            }
        )
    }

    override suspend fun setSexRatingFilter(sex: String?) {
        ratingFilterDao.updateSex(sex)
    }

    override suspend fun setMaxAge(max: Int) {
        ratingFilterDao.updateMaxAge(max)
    }

    override suspend fun setMinAge(min: Int) {
        ratingFilterDao.updateMinAge(min)
    }

    override suspend fun insertDefault() {
        ratingFilterDao.insert(
            EntityRatingFilter(
                type = null,
                sex = null,
                city_id = null,
                country_id = null,
                age_between_max = 30,
                age_between_min = 0,
                id = null,
                breed_id = null
          )
        )
    }
}