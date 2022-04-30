package com.petsvote.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.petsvote.core.adapter.Item
import com.petsvote.data.repository.paging.RatingPagingRepository.Companion.NETWORK_PAGE_SIZE
import com.petsvote.domain.entity.pet.RatingFilterLocationType
import com.petsvote.domain.entity.pet.RatingPet
import com.petsvote.domain.entity.pet.RatingPetItemType
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
        val countUserPets = 0;
        val isWorlTop = false
        return try {
            var repos: MutableList<RatingPet> = ratingRepository.getRating(
                limit = null,
                offset = offset).toMutableList()

            val nextKey = if (repos.size < NETWORK_PAGE_SIZE) {
                null
            } else {
                offset + NETWORK_PAGE_SIZE
            }

            val itemIndex1: RatingPet? = repos.find { it.index == 1}
            itemIndex1?.let {
                repos.find { it.index == 1 }?.itemType ?: RatingPetItemType.TOP
            }

            if(countUserPets == 0 && itemIndex1 != null){
                when(repos.size){
                    0 -> repos.add(generateNullableRatingPet(true))
                    1 -> repos.add(1, generateNullableRatingPet())
                    2 -> repos.add(2, generateNullableRatingPet())
                    3 -> repos.add(3, generateNullableRatingPet())
                    else -> repos.add(3, generateNullableRatingPet())
                }
            }

            if(itemIndex1 != null && isWorlTop){
                repos.find { it.index == 1 }?.locationType ?:RatingFilterLocationType.WORLD
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

    private fun generateNullableRatingPet(isTop: Boolean = false): RatingPet{
        return RatingPet(-1, -1, "", "", "", RatingFilterLocationType.COUNTRY,
            emptyList(), false, if(!isTop) RatingPetItemType.ADDPET else RatingPetItemType.TOPADDPET)
    }

    companion object {
        private const val DEFAULT_OFFSET = 0
    }

}