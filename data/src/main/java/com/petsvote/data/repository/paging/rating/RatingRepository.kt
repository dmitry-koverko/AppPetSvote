package com.petsvote.data.repository.paging.rating

import com.petsvote.data.mappers.checkResult
import com.petsvote.data.mappers.checkResultPaging
import com.petsvote.data.mappers.remoteToRatingList
import com.petsvote.data.mappers.remoteToVotePetsList
import com.petsvote.domain.entity.params.AddVoteParams
import com.petsvote.domain.entity.pet.RatingPet
import com.petsvote.domain.entity.pet.VotePet
import com.petsvote.domain.repository.IUserRepository
import com.petsvote.domain.repository.rating.IRatingFilterRepository
import com.petsvote.domain.repository.rating.RatingRepository
import com.petsvote.domain.usecases.configuration.GetLocaleLanguageCodeUseCase
import com.petsvote.retrofit.api.RatingApi
import com.petsvote.retrofit.entity.rating.Rating
import com.petsvote.retrofit.entity.rating.Vote
import com.petsvote.room.dao.BreedsDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RatingRepository @Inject constructor(
    private val ratingApi: RatingApi,
    private val userRepository: IUserRepository,
    private val languageCodeUseCase: GetLocaleLanguageCodeUseCase,
    private val ratingFilterRepository: IRatingFilterRepository,
    private val breedsDao: BreedsDao
) : RatingRepository {

    val scoupe = CoroutineScope(Dispatchers.IO + Job())

    override suspend fun getRating(
        offset: Int,
        limit: Int?,
        breedId: Int?,
        rating_type: String
    ): List<RatingPet> {

        val filter =
            withContext(scoupe.coroutineContext) { ratingFilterRepository.getSimpleRatingFilter() }
        val user = withContext(scoupe.coroutineContext) {userRepository.getCurrentUser()}

        val response = checkResultPaging<Rating>(
            ratingApi.getRating(
                userRepository.getToken(),
                limit = limit,
                offset = offset,
                lang = languageCodeUseCase.getLanguage(),
                filter.type,
                filter.sex,
                city_id = user.location?.city_id,
                country_id = user.location?.country_id,
                "${filter.age_between_min}:${filter.age_between_max}",
                rating_type = filter.rating_type?.nameParams,
                id = breedId,
                breed_id = null
            )

        )
        return if (response != null) (response as Rating).pets.remoteToRatingList() else emptyList()
    }

    override suspend fun getRatingUserPet(
        breedId: Int?
    ): List<RatingPet> {

        val filter =
            withContext(scoupe.coroutineContext) { ratingFilterRepository.getSimpleRatingFilter() }
        val user = withContext(scoupe.coroutineContext) {userRepository.getCurrentUser()}

        val response = checkResultPaging<Rating>(
            ratingApi.getRating(
                userRepository.getToken(),
                limit = 50,
                offset = 0,
                lang = languageCodeUseCase.getLanguage(),
                null,
                null,
                city_id = user.location?.city_id,
                country_id = user.location?.country_id,
                "${filter.age_between_min}:${filter.age_between_max}",
                rating_type = filter.rating_type?.nameParams,
                id = breedId,
                breed_id = null
            )

        )
        return if (response != null) (response as Rating).pets.remoteToRatingList() else emptyList()
    }

    override suspend fun getVotePets(): Flow<List<VotePet>> = flow {
        run {
            val filter =
                withContext(scoupe.coroutineContext) { ratingFilterRepository.getSimpleRatingFilter() }
            val breeds =
                withContext(scoupe.coroutineContext) { breedsDao.getBreedsByLang(languageCodeUseCase.getLanguage()) }
            val result =
                checkResult(
                    ratingApi.getVotePets(
                        userRepository.getToken(),
                        null,
                        null,
                        languageCodeUseCase.getLanguage(),
                        filter.type,
                        null,
                        filter.city_id,
                        filter.country_id,
                        null,
                        filter.rating_type?.name,
                        null
                    )
                )
            if (result != null) emit(
                (result as Vote).pets.remoteToVotePetsList(
                    filter.rating_type,
                    breeds
                )
            )
            else emit(emptyList())
        }
    }

    override suspend fun addVote(params: AddVoteParams) {
        val user = withContext(scoupe.coroutineContext) { userRepository.getCurrentUser() }
        ratingApi.addVote(
            token = userRepository.getToken(),
            from_user_id = user.id,
            to_pet_id = params.id,
            mark = params.mark,
            grant_point = 0
        )
    }

}