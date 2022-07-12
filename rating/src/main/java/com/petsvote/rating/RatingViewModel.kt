package com.petsvote.rating

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.petsvote.core.BaseViewModel
import com.petsvote.core.adapter.Item
import com.petsvote.domain.entity.filter.RatingFilterType
import com.petsvote.domain.entity.user.UserPet
import com.petsvote.domain.usecases.filter.*
import com.petsvote.domain.usecases.filter.impl.GetRatingFilterTextUseCase
import com.petsvote.domain.usecases.rating.GetRatingUseCase
import com.petsvote.domain.usecases.user.ICheckLocationUserUseCase
import com.petsvote.domain.usecases.user.IGetUserPetsUseCase
import com.petsvote.ui.maintabs.BesieTabSelected
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RatingViewModel @Inject constructor(
    private val ratingUseCase: GetRatingUseCase,
    private val userPetsUseCase: IGetUserPetsUseCase,
    private val getRatingFilterUseCase: IGetRatingFilterUseCase,
    private val setBreedIdInRatingFilterUseCase: ISetBreedIdInRatingFilterUseCase,
    private val ratingFilterTypeUseCase: IGetRatingFilterTypeUseCase,
    private val checkLocationUserUseCase: ICheckLocationUserUseCase,
    private val setRatingFilterTypeUseCase: ISetRatingFilterTypeUseCase,
    private val getRatingFilterTextUseCase: IGetRatingFilterTextUseCase
) : BaseViewModel() {

    var pages = MutableStateFlow<PagingData<Item>?>(null)
    var userPets = MutableStateFlow<List<UserPet>>(emptyList())
    var filterType = MutableStateFlow(RatingFilterType.GLOBAL)
    var isLocationUser = MutableStateFlow(false)
    var filterText = MutableStateFlow<String?>(null)

    suspend fun getRating() = withContext(Dispatchers.IO){
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

    suspend fun getRatingFilter()= withContext(Dispatchers.IO){
        getRatingFilterTextUseCase.getFilter().collect {
            filterText.emit(it)
        }
    }

    suspend fun getRatingFilterType()= withContext(Dispatchers.IO){
        filterType.emit(ratingFilterTypeUseCase.getRatingFilterType())
    }

    suspend fun checkLocation()= withContext(Dispatchers.IO){
        isLocationUser.emit(checkLocationUserUseCase.checkLocationUser())
    }

    fun setBreedId(breedId: Int){
        viewModelScope.launch {
            setBreedIdInRatingFilterUseCase.setBredIdRatingFilter(breedId = breedId)
        }
    }

    fun setFilterType(tab: BesieTabSelected){
        var type = when(tab){
            BesieTabSelected.WORLD -> RatingFilterType.GLOBAL
            BesieTabSelected.COUNTRY -> RatingFilterType.COUNTRY
            BesieTabSelected.CITY -> RatingFilterType.CITY
            else -> RatingFilterType.GLOBAL
        }
        viewModelScope.launch {
            setRatingFilterTypeUseCase.setRatingFilterType(type)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val ratingUseCase: GetRatingUseCase,
        private val getUserPetsUseCase: IGetUserPetsUseCase,
        private val getRatingFilterUseCase: IGetRatingFilterUseCase,
        private val setBreedIdInRatingFilterUseCase: ISetBreedIdInRatingFilterUseCase,
        private val ratingFilterTypeUseCase: IGetRatingFilterTypeUseCase,
        private val checkLocationUserUseCase: ICheckLocationUserUseCase,
        private val setRatingFilterTypeUseCase: ISetRatingFilterTypeUseCase,
        private val getRatingFilterTextUseCase: IGetRatingFilterTextUseCase
    )
        : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == RatingViewModel::class.java)
            return RatingViewModel(
                ratingUseCase = ratingUseCase,
                userPetsUseCase = getUserPetsUseCase,
                getRatingFilterUseCase = getRatingFilterUseCase,
                setBreedIdInRatingFilterUseCase = setBreedIdInRatingFilterUseCase,
                ratingFilterTypeUseCase = ratingFilterTypeUseCase,
                checkLocationUserUseCase = checkLocationUserUseCase,
                setRatingFilterTypeUseCase = setRatingFilterTypeUseCase,
            getRatingFilterTextUseCase = getRatingFilterTextUseCase) as T
        }

    }
}