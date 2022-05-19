package com.petsvote.data.repository.paging

import android.util.Log
import com.petsvote.data.mappers.checkResultPaging
import com.petsvote.data.mappers.remoteToRatingList
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

    override suspend fun getVotePets(): Flow<List<VotePet>> = flow {
        run{
            emit(listOf(
                VotePet(-1, 1, "Eshka, 7m.", "Cat, Minsk, Belarus", listOf(
                    "https://sobakainfo.ru/wp-content/uploads/2016/11/1-66.jpg",
                    "https://sobakainfo.ru/wp-content/uploads/2016/11/1-66.jpg"
                )),
                VotePet(-1, 1, "Deshama, 7m.", "Fish, Minsk, Belarus", listOf(
                    "https://sobakainfo.ru/wp-content/uploads/2016/11/1-66.jpg",
                    "https://sobakainfo.ru/wp-content/uploads/2016/11/1-66.jpg",
                    "https://sobakainfo.ru/wp-content/uploads/2016/11/1-66.jpg",
                    "https://sobakainfo.ru/wp-content/uploads/2016/11/1-66.jpg"
                )),
                VotePet(-1, 1, "Petka, 12", "Dog, Minsk, Belarus", listOf(
                    "https://sobakainfo.ru/wp-content/uploads/2016/11/1-66.jpg",
                    "https://sobakainfo.ru/wp-content/uploads/2016/11/1-66.jpg",
                    "https://sobakainfo.ru/wp-content/uploads/2016/11/1-66.jpg"
                )),
                VotePet(-1, 1, "Vatsksf, 7m.", "Cat, Minsk, Belarus", listOf(
                    "https://sobakainfo.ru/wp-content/uploads/2016/11/1-66.jpg"
                ))
            ))
        }
    }
    //{
//        val response = checkResultPaging<Rating>(
//            ratingApi.getVotePets(null, null, null, null, null, null, null, null, null, null)
//        )
        //return if (response != null) (response as Rating).pets.remoteToRatingList() else emptyList()

   // }
}