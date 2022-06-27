package com.petswote.pet.breeds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.petsvote.core.BaseViewModel
import com.petsvote.core.adapter.Item
import com.petsvote.domain.entity.breed.Breed
import com.petsvote.domain.usecases.configuration.IGetBreedsUseCase
import com.petsvote.domain.usecases.filter.IGetBreedsPagingUseCase
import com.petsvote.domain.usecases.filter.IGetRatingFilterUseCase
import com.petsvote.domain.usecases.filter.ISetBreedUseCase
import com.petsvote.domain.usecases.pet.create.IPetGetBreedsPagingUseCase
import com.petsvote.domain.usecases.pet.create.ISetPetBreedUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PetSelectBreedsViewModel @Inject constructor(
    private val breedsPagingUseCase: IPetGetBreedsPagingUseCase,
    private val setPetBreedUseCase: ISetPetBreedUseCase
) : BaseViewModel() {

    private var _breeds = MutableStateFlow<List<Breed>>(emptyList())

    var breeds = MutableStateFlow<List<Breed>>(emptyList())
    var pages = MutableStateFlow<PagingData<Item>?>(null)
    var progress = MutableStateFlow<Boolean>(true)
    var isSelect = MutableStateFlow<Boolean>(false)
    var topSelect = MutableStateFlow<Int?>(-3)

    suspend fun getBreedsPaging() = withContext(Dispatchers.IO){
        breedsPagingUseCase.getRating().cachedIn(viewModelScope).collect{
            pages.emit(it)
            progress.emit(false)
        }

    }

//    suspend fun getBreeds() = withContext(Dispatchers.IO) {
//        ratingFilterTypeUseCase.getRatingFilter().collect {
//            topSelect.emit(it.breed_id)
//            progress.emit(false)
//        }
//
//    }

    fun clearFilter(){
        viewModelScope.launch {
            breeds.emit(_breeds.first())
        }
    }

    fun setTextFilter(text: String){
        viewModelScope.launch {
            breedsPagingUseCase.getRating(text).cachedIn(viewModelScope).collect{
                pages.emit(it)
            }
        }
    }

    fun setBreedFilter(breed: Item) {
        viewModelScope.launch {
           setPetBreedUseCase.setBreed(breed)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val breedsPagingUseCase: IPetGetBreedsPagingUseCase,
        private val setPetBreedUseCase: ISetPetBreedUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == PetSelectBreedsViewModel::class.java)
            return PetSelectBreedsViewModel(
                breedsPagingUseCase = breedsPagingUseCase,
                setPetBreedUseCase = setPetBreedUseCase
            ) as T
        }

    }
}