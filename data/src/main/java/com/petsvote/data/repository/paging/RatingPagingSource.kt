package com.petsvote.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.petsvote.core.adapter.Item
import com.petsvote.data.repository.RatingRepository.Companion.NETWORK_PAGE_SIZE
import com.petsvote.domain.entity.pet.RatingPet
import com.petsvote.retrofit.api.RatingApi
import java.io.IOException
import javax.inject.Inject

class RatingPagingSource @Inject constructor(
    private val ratingApi: RatingApi
): PagingSource<Int, Item>() {

    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        val position = params.key ?: Companion.GITHUB_STARTING_PAGE_INDEX
        return try {
            //val response = service.searchRepos(apiQuery, position, params.loadSize)
            //val repos = response.items
            val repos = generateRatingPet(position)
            val nextKey = if (repos.isEmpty()) {
                null
            } else {
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = repos,
                prevKey = if (position == Companion.GITHUB_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        }
    }

    companion object {
        private const val GITHUB_STARTING_PAGE_INDEX = 1
    }

    fun generateRatingPet(postion: Int): List<Item> {
        var list = mutableListOf<RatingPet>()
        for (i in postion..postion + 50) list.add(RatingPet(i, false))
        return list
    }
}