package com.petsvote.domain.usecases.filter.impl

import com.petsvote.domain.entity.filter.RatingFilterType
import com.petsvote.domain.repository.IBreedRepository
import com.petsvote.domain.repository.IResourcesRepository
import com.petsvote.domain.repository.IUserRepository
import com.petsvote.domain.repository.rating.IRatingFilterRepository
import com.petsvote.domain.usecases.filter.IGetRatingFilterTextUseCase
import com.petsvote.domain.usecases.pet.create.impl.GetKindPetUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRatingFilterTextUseCase @Inject constructor(
    private val ratingFilterRepository: IRatingFilterRepository,
    private val resourcesRepository: IResourcesRepository,
    private val userRepository: IUserRepository,
    private val breedRepository: IBreedRepository
): IGetRatingFilterTextUseCase {


    override suspend fun getFilter(): Flow<String> = flow {
        ratingFilterRepository.getRatingFilter().collect {
            var user = userRepository.getCurrentUser()
            var builder = StringBuilder()
            builder.append(
                when(it.rating_type){
                    RatingFilterType.GLOBAL -> resourcesRepository.getStringUiByName("world")
                    RatingFilterType.COUNTRY -> user.location?.country
                    RatingFilterType.CITY -> user.location?.city
                    else -> resourcesRepository.getStringUiByName("world")
                }
            )
            builder.append(", ")
            builder.append(
                when{
                    it.type == null -> resourcesRepository.getStringUiByName("all_kinds").lowercase()
                    it.type.contains(",") -> resourcesRepository.getStringUiByName("n_kinds").lowercase()
                    else -> resourcesRepository.getStringUiByName(it.type).lowercase()
                }
            )
            builder.append(", ")

            builder.append(
                when(it.breed_id){
                    null -> resourcesRepository.getStringUiByName("all_breeds").lowercase()
                    -1 -> resourcesRepository.getStringUiByName("no_breeds").lowercase()
                    else -> breedRepository.getBreedByBreedId(it.breed_id)?.breedName?.lowercase()
                }
            )

            builder.append(", ")

            builder.append(
                when(it.sex){
                    null -> resourcesRepository.getStringUiByName("dm").lowercase()
                    "MALE" -> resourcesRepository.getStringUiByName("sex_man").lowercase()
                    "FEMALE" -> resourcesRepository.getStringUiByName("sex_girl").lowercase()
                    else -> resourcesRepository.getStringUiByName("dm").lowercase()
                }
            )

            builder.append(", ")
            builder.append("${it.age_between_min}-${it.age_between_max}")

            emit(builder.toString())
        }
    }
}