package com.petsvote.data.repository

import android.content.Context
import com.petsvote.data.mappers.checkResult
import com.petsvote.data.mappers.remoteToLocalBreedsList
import com.petsvote.domain.entity.breed.Breed
import com.petsvote.domain.repository.IBreedRepository
import com.petsvote.domain.usecases.configuration.GetLocaleLanguageCodeUseCase
import com.petsvote.retrofit.api.ConfigurationApi
import com.petsvote.room.dao.BreedsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BreedRepository @Inject constructor(
    private val breedsDao: BreedsDao,
    private val configurationApi: ConfigurationApi,
    private val localeLanguageCodeUseCase: GetLocaleLanguageCodeUseCase

) : IBreedRepository {

    override suspend fun getBreeds(): List<Breed> {
        return listOf(

        )
    }



    override suspend fun updateBreeds() {
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