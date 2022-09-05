package com.petsvote.data.repository.paging.rating

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.petsvote.core.adapter.Item
import com.petsvote.domain.repository.IUserRepository
import com.petsvote.domain.repository.rating.IRatingFilterRepository
import com.petsvote.domain.repository.rating.RatingPagingRepository
import com.petsvote.domain.repository.rating.RatingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RatingPagingRepository @Inject constructor(
    private val ratingRepository: RatingRepository,
    private val ratingFilterRepository: IRatingFilterRepository,
    private val userRepository: IUserRepository
) : RatingPagingRepository {

    override fun getRating(): Flow<PagingData<Item>> {

        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                RatingPagingSource(
                    ratingRepository = ratingRepository,
                    ratingFilterRepository = ratingFilterRepository,
                    userRepository = userRepository
                )
            }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 50
    }
}