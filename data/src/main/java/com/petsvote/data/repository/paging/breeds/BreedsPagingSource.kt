package com.petsvote.data.repository.paging.breeds

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.petsvote.core.adapter.Item
import com.petsvote.domain.repository.IBreedRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import javax.inject.Inject

class BreedsPagingSource @Inject constructor(
    private val breedsRepository: IBreedRepository,
    private val text: String?
) : PagingSource<Int, Item>() {

    private val scope = CoroutineScope(Dispatchers.IO + Job())

    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        var offset = params.key ?: DEFAULT_OFFSET
        if(!text.isNullOrEmpty()) {
            offset = 0
            limit = 1000
        }
        return try {

            var results = scope.async { breedsRepository.getBreeds(offset, text ?: "", limit) }.await()

            var nextKey = if (results.size < BreedsPagingRepository.NETWORK_PAGE_SIZE) {
                null
            } else {
                offset + BreedsPagingRepository.NETWORK_PAGE_SIZE
            }

            var prevKey = if (offset == DEFAULT_OFFSET) null else offset - BreedsPagingRepository.NETWORK_PAGE_SIZE
            if(prevKey == nextKey) {
                prevKey = null
                nextKey = BreedsPagingRepository.NETWORK_PAGE_SIZE
            }
            if(!text.isNullOrEmpty()) {
                prevKey = null
                nextKey = null
            }
            LoadResult.Page(
                data = results,
                prevKey = prevKey,
                nextKey = nextKey,
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }


    companion object {
        private const val DEFAULT_OFFSET = 0
        private var limit = 50
    }

}