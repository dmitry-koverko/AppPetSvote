package com.petsvote.data.repository.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.petsvote.core.adapter.Item
import com.petsvote.domain.repository.rating.RatingPagingRepository
import com.petsvote.domain.repository.rating.RatingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RatingPagingRepository @Inject constructor(
    private val ratingRepository: RatingRepository
) : RatingPagingRepository {

    override fun getRating(): Flow<PagingData<Item>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { RatingPagingSource(ratingRepository) }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 50
    }
}