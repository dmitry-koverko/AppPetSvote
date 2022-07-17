package com.petsvote.data.repository.paging.rating

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.petsvote.core.adapter.Item
import com.petsvote.data.repository.paging.rating.RatingPagingRepository.Companion.NETWORK_PAGE_SIZE
import com.petsvote.domain.entity.filter.RatingFilterType
import com.petsvote.domain.entity.pet.RatingFilterLocationType
import com.petsvote.domain.entity.pet.RatingPet
import com.petsvote.domain.entity.pet.RatingPetItemType
import com.petsvote.domain.repository.IUserRepository
import com.petsvote.domain.repository.rating.IRatingFilterRepository
import com.petsvote.domain.repository.rating.RatingRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class RatingPagingSource @Inject constructor(
    private val ratingRepository: RatingRepository,
    private val ratingFilterRepository: IRatingFilterRepository,
    private val userRepository: IUserRepository
) : PagingSource<Int, Item>() {

    val scoupe = CoroutineScope(Dispatchers.IO + Job())

    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {

        val userPets = scoupe.async { userRepository.getSimpleUserPets() }.await()
        val countUserPets = userPets.size
        val ratingFilter = scoupe.async { ratingFilterRepository.getSimpleRatingFilter() }.await()
        Log.d("RATINGPAGINGSOURCES", ratingFilter.rating_type?.nameParams.toString())
        val isWorldTop = ratingFilter.rating_type == RatingFilterType.GLOBAL

        var offset = params.key ?: DEFAULT_OFFSET
        if (ratingFilter.breed_id != null) offset = 0
        return try {

            var repos: MutableList<RatingPet> = ratingRepository.getRating(
                limit = if (offset < NETWORK_PAGE_SIZE && offset!= 0) offset else NETWORK_PAGE_SIZE,
                offset = if (offset < NETWORK_PAGE_SIZE) 0 else offset,
                breedId = ratingFilter.breed_id,
                rating_type = ratingFilter.rating_type?.nameParams ?: RatingFilterType.GLOBAL.nameParams
            ).toMutableList()
            ratingFilterRepository.setBredIdRatingFilter(null)

            val nextKey = if (repos.size < NETWORK_PAGE_SIZE) {
                null
            } else {
                repos.last().index
            }


            val itemIndex1: RatingPet? = repos.find { it.index == 1 }
            itemIndex1?.let {
                repos.find { it.index == 1 }?.itemType ?: RatingPetItemType.TOP
            }

            if (countUserPets == 0 && itemIndex1 != null) {
                when (repos.size) {
                    0 -> repos.add(generateNullableRatingPet(true))
                    1 -> repos.add(1, generateNullableRatingPet())
                    2 -> repos.add(2, generateNullableRatingPet())
                    3 -> repos.add(3, generateNullableRatingPet())
                    else -> repos.add(3, generateNullableRatingPet())
                }
            } else if (countUserPets != 0) {
                userPets.onEach { userPet ->
                    repos.find { it.pet_id == userPet.pets_id }?.isUserPet = true
                }
            }
            repos.onEach { it.locationType = when(ratingFilter.rating_type){
                RatingFilterType.GLOBAL -> RatingFilterLocationType.WORLD
                RatingFilterType.COUNTRY -> RatingFilterLocationType.COUNTRY
                RatingFilterType.CITY -> RatingFilterLocationType.CITY
                else -> RatingFilterLocationType.WORLD
            } }

            if (itemIndex1 != null && isWorldTop) {
                repos.find { it.index == 1 }?.locationType = RatingFilterLocationType.WORLD
            }

            var prev = if (repos.first().index < NETWORK_PAGE_SIZE && repos.first().index == 1) null
            else if (repos.first().index < NETWORK_PAGE_SIZE && repos.first().index != 1) repos.first().index
            else repos.first().index - 1 - NETWORK_PAGE_SIZE

            LoadResult.Page(
                data = repos,
                prevKey = prev,
                nextKey = nextKey,
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    private fun generateNullableRatingPet(isTop: Boolean = false): RatingPet {
        return RatingPet(
            -1,
            -1,
            "",
            "",
            "",
            RatingFilterLocationType.COUNTRY,
            emptyList(),
            false,
            if (!isTop) RatingPetItemType.ADDPET else RatingPetItemType.TOPADDPET,
            -1,
            ""
        )
    }

    companion object {
        private const val DEFAULT_OFFSET = 0
    }

}