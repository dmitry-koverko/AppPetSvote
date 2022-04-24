package com.petsvote.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.petsvote.core.adapter.Item
import com.petsvote.data.repository.paging.RatingPagingSource
import com.petsvote.domain.entity.pet.RatingPet
import com.petsvote.domain.repository.RatingRepository
import com.petsvote.retrofit.api.RatingApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RatingRepository @Inject constructor(
    private val ratingApi: RatingApi
) : RatingRepository {

    override fun getRating(): Flow<PagingData<Item>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { RatingPagingSource(ratingApi) }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 50
    }
}