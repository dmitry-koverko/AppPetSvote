package com.petsvote.data.repository.paging

import com.petsvote.data.mappers.checkResultPaging
import com.petsvote.data.mappers.remoteToRatingList
import com.petsvote.domain.entity.pet.RatingPet
import com.petsvote.domain.repository.IUserRepository
import com.petsvote.domain.repository.rating.RatingRepository
import com.petsvote.domain.usecases.configuration.GetLocaleLanguageCodeUseCase
import com.petsvote.retrofit.api.RatingApi
import com.petsvote.retrofit.entity.rating.Rating
import javax.inject.Inject

class RatingRepository @Inject constructor(
    private val ratingApi: RatingApi,
    private val userRepository: IUserRepository,
    private val languageCodeUseCase: GetLocaleLanguageCodeUseCase
) : RatingRepository {

    override suspend fun getRating(offset: Int, limit: Int?, breedId: Int?): List<RatingPet> {
        val response = checkResultPaging<Rating>(
            ratingApi.getRating(
                userRepository.getToken(),
                limit = limit,
                offset = offset,
                lang = languageCodeUseCase.getLanguage(),
                null,
                null,
                282,
                3,
                null,
                "city",
                id = breedId,
                breed_id = null
            )
        )
        return if (response != null) (response as Rating).pets.remoteToRatingList() else emptyList()
    }
}