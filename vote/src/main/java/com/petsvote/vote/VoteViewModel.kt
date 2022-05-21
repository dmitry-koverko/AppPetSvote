package com.petsvote.vote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.petsvote.core.BaseViewModel
import com.petsvote.core.adapter.Item
import com.petsvote.core.ext.log
import com.petsvote.domain.entity.filter.RatingFilterType
import com.petsvote.domain.entity.pet.VotePet
import com.petsvote.domain.entity.user.UserPet
import com.petsvote.domain.usecases.filter.IGetRatingFilterTypeUseCase
import com.petsvote.domain.usecases.filter.IGetRatingFilterUseCase
import com.petsvote.domain.usecases.filter.ISetBreedIdInRatingFilterUseCase
import com.petsvote.domain.usecases.filter.ISetRatingFilterTypeUseCase
import com.petsvote.domain.usecases.rating.GetRatingUseCase
import com.petsvote.domain.usecases.rating.IGetVotePetsUseCase
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

class VoteViewModel @Inject constructor(
    private val votePetsUseCase: IGetVotePetsUseCase
) : BaseViewModel() {

    var pets = MutableStateFlow<List<VotePet>>(emptyList())

    fun getRating(){
        viewModelScope.launch {
            votePetsUseCase.getRating(pets.value.size).collect {
                pets.emit(it)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val votePetsUseCase: IGetVotePetsUseCase
    )
        : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == VoteViewModel::class.java)
            return VoteViewModel(
                votePetsUseCase = votePetsUseCase) as T
        }

    }
}