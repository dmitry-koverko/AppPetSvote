package com.petsvote.data.repository

import com.petsvote.data.mappers.toRatingFilter
import com.petsvote.domain.entity.filter.RatingFilter
import com.petsvote.domain.repository.rating.IRatingFilterRepository
import com.petsvote.room.dao.RatingFilterDao
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
}