package com.petsvote.data.repository.paging

import android.util.Log
import com.petsvote.data.mappers.checkResult
import com.petsvote.data.mappers.checkResultPaging
import com.petsvote.data.mappers.remoteToRatingList
import com.petsvote.data.mappers.remoteToVotePetsList
import com.petsvote.data.repository.RatingFilterRepository
import com.petsvote.domain.entity.pet.RatingPet
import com.petsvote.domain.entity.pet.VotePet
import com.petsvote.domain.entity.user.Photo
import com.petsvote.domain.repository.IUserRepository
import com.petsvote.domain.repository.rating.IRatingFilterRepository
import com.petsvote.domain.repository.rating.RatingRepository
import com.petsvote.domain.usecases.configuration.GetLocaleLanguageCodeUseCase
import com.petsvote.domain.usecases.filter.IGetRatingFilterUseCase
import com.petsvote.domain.usecases.filter.impl.GetRatingFilterUseCase
import com.petsvote.retrofit.api.RatingApi
import com.petsvote.retrofit.entity.rating.Rating
import com.petsvote.retrofit.entity.rating.Vote
import com.petsvote.retrofit.entity.rating.VoteRating
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RatingRepository @Inject constructor(
    private val ratingApi: RatingApi,
    private val userRepository: IUserRepository,
    private val languageCodeUseCase: GetLocaleLanguageCodeUseCase,
    private val ratingFilterRepository: IRatingFilterRepository
) : RatingRepository {

    override suspend fun getRating(
        offset: Int,
        limit: Int?,
        breedId: Int?,
        rating_type: String
    ): List<RatingPet> {

        val response = checkResultPaging<Rating>(
            ratingApi.getRating(
                userRepository.getToken(),
                limit = limit,
                offset = offset,
                lang = languageCodeUseCase.getLanguage(),
                null,
                null,
                city_id = null,//userRepository.getCurrentUser().location?.city_id,
                country_id = null,//userRepository.getCurrentUser().location?.country_id,
                null,
                rating_type = rating_type,//ratingFilterRepository.getSimpleRatingFilter().rating_type?.nameParams,
                id = breedId,
                breed_id = null
            )

        )
        return if (response != null) (response as Rating).pets.remoteToRatingList() else emptyList()
    }

    override suspend fun getVotePets(offset: Int): Flow<List<VotePet>> = flow {
        run {
            val result =
                checkResult(
                    ratingApi.getVotePets(
                        userRepository.getToken(),
                        null,
                        offset,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                    )
                )
            if (result != null) emit((result as Vote).pets.remoteToVotePetsList())
            else emit(emptyList())
        }
    }

}