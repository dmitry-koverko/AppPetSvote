package com.petsvote.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.petsvote.core.adapter.Item
import com.petsvote.data.repository.paging.RatingPagingRepository.Companion.NETWORK_PAGE_SIZE
import com.petsvote.domain.entity.pet.RatingPet
import com.petsvote.domain.repository.rating.RatingRepository
import java.io.IOException
import javax.inject.Inject

class RatingPagingSource @Inject constructor(
    private val ratingRepository: RatingRepository
) : PagingSource<Int, Item>() {

    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        val offset = params.key ?: DEFAULT_OFFSET
        return try {
            val repos = ratingRepository.getRating(
                limit = null,
                offset = offset)

            val nextKey = if (repos.isEmpty()) {
                null
            } else {
                offset + NETWORK_PAGE_SIZE
            }
            LoadResult.Page(
                data = repos,
                prevKey = if (offset == Companion.DEFAULT_OFFSET) null else offset - NETWORK_PAGE_SIZE,
                nextKey = nextKey,
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        }
    }

    companion object {
        private const val DEFAULT_OFFSET = 0
    }

    fun generateRatingPet(postion: Int): List<Item> {
        var list = mutableListOf<RatingPet>()
        for (i in postion until postion + NETWORK_PAGE_SIZE)
            list.add(RatingPet(i, false))
        return list
    }
}