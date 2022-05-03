package com.petsvote.rating

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.petsvote.core.BaseViewModel
import com.petsvote.core.adapter.Item
import com.petsvote.core.ext.log
import com.petsvote.domain.entity.user.UserPet
import com.petsvote.domain.usecases.rating.GetRatingUseCase
import com.petsvote.domain.usecases.rating.IGetRatingFilterUseCase
import com.petsvote.domain.usecases.rating.ISetBreedIdInRatingFilterUseCase
import com.petsvote.domain.usecases.rating.impl.GetRatingFilterUseCase
import com.petsvote.domain.usecases.rating.impl.SetBreedIdInRatingFilterUseCase
import com.petsvote.domain.usecases.user.IGetUserPetsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RatingViewModel @Inject constructor(
    private val ratingUseCase: GetRatingUseCase,
    private val userPetsUseCase: IGetUserPetsUseCase,
    private val getRatingFilterUseCase: IGetRatingFilterUseCase,
    private val setBreedIdInRatingFilterUseCase: ISetBreedIdInRatingFilterUseCase
) : BaseViewModel() {

    var pages = MutableStateFlow<PagingData<Item>?>(null)
    var userPets = MutableStateFlow<List<UserPet>>(emptyList())

    suspend fun getRating() = withContext(Dispatchers.Default){
        setBreedIdInRatingFilterUseCase.setBredIdRatingFilter(null)
        ratingUseCase.getRating().cachedIn(viewModelScope).collect{
            pages.emit(it)
        }

    }

    suspend fun getUserPets(){
        userPetsUseCase.getUserPets().collect {
            userPets.emit(it)
        }
    }

    suspend fun getRatingFilter(){
        getRatingFilterUseCase.getRatingFilter().onEach {
            log(it.toString())
        }
    }


    fun setBreedId(breedId: Int){
        viewModelScope.launch {
            setBreedIdInRatingFilterUseCase.setBredIdRatingFilter(breedId = breedId)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val ratingUseCase: GetRatingUseCase,
        private val getUserPetsUseCase: IGetUserPetsUseCase,
        private val getRatingFilterUseCase: IGetRatingFilterUseCase,
        private val setBreedIdInRatingFilterUseCase: ISetBreedIdInRatingFilterUseCase
    )
        : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == RatingViewModel::class.java)
            return RatingViewModel(
                ratingUseCase = ratingUseCase,
                userPetsUseCase = getUserPetsUseCase,
                getRatingFilterUseCase = getRatingFilterUseCase,
                setBreedIdInRatingFilterUseCase = setBreedIdInRatingFilterUseCase) as T
        }

    }
}