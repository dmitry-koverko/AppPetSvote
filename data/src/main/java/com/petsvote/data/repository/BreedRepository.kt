package com.petsvote.data.repository

import android.content.Context
import com.petsvote.data.mappers.checkResult
import com.petsvote.data.mappers.remoteToLocalBreedsList
import com.petsvote.data.mappers.toLocalBreedsList
import com.petsvote.domain.entity.breed.Breed
import com.petsvote.domain.repository.IBreedRepository
import com.petsvote.domain.repository.rating.IRatingFilterRepository
import com.petsvote.domain.usecases.configuration.GetLocaleLanguageCodeUseCase
import com.petsvote.retrofit.api.ConfigurationApi
import com.petsvote.room.dao.BreedsDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BreedRepository @Inject constructor(
    private val breedsDao: BreedsDao,
    private val configurationApi: ConfigurationApi,
    private val localeLanguageCodeUseCase: GetLocaleLanguageCodeUseCase,
    private val filterRepository: IRatingFilterRepository

) : IBreedRepository {

    private val scope = CoroutineScope(Dispatchers.IO + Job())

    override suspend fun getBreeds(offset: Int, text: String, limit: Int): List<Breed> {
        var filter = scope.async { filterRepository.getSimpleRatingFilter() }.await()
        var results =
            breedsDao.getBreedsByKinds(localeLanguageCodeUseCase.getLanguage(), filter.type, offset, text, limit)
                .toLocalBreedsList()
        filter.breed_id?.let { id ->
            results.find { it.breedId == id }?.isSelect = true
        }
        return results
    }


    override suspend fun updateBreeds() {
        scope.async { breedsDao.deleteAll() }.await()
        checkResult(
            configurationApi.getBreeds(
                null,
                localeLanguageCodeUseCase.getLanguage()
            )
        )?.let {
            breedsDao.insert(
                it.remoteToLocalBreedsList()
            )
        }
    }

    override suspend fun getBreedById(id: Int): Flow<List<Breed>> {
        return flow {

        }
    }

}