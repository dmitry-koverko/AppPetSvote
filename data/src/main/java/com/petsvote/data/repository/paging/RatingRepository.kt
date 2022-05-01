package com.petsvote.data.repository.paging

import com.petsvote.data.mappers.checkResultPaging
import com.petsvote.data.mappers.remoteToRatingList
import com.petsvote.domain.entity.pet.RatingPet
import com.petsvote.domain.repository.IUserRepository
import com.petsvote.domain.repository.rating.RatingRepository
import com.petsvote.retrofit.api.RatingApi
import com.petsvote.retrofit.entity.rating.Rating
import javax.inject.Inject

class RatingRepository @Inject constructor(
    private val ratingApi: RatingApi,
    private val userRepository: IUserRepository
): RatingRepository {

    override suspend fun getRating(offset: Int, limit: Int?): List<RatingPet> {
        val response = checkResultPaging<Rating>(ratingApi.getRating(
                userRepository.getToken(),
                limit = limit,
                offset = offset,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
            ))
        return if(response != null) (response as Rating).pets.remoteToRatingList() else emptyList()
    }
}