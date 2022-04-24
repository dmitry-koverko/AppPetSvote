package com.petsvote.rating

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.petsvote.core.BaseViewModel
import com.petsvote.core.adapter.Item
import com.petsvote.domain.entity.pet.RatingPet
import com.petsvote.domain.entity.user.DataResponse
import com.petsvote.domain.entity.user.RegisterUserParams
import com.petsvote.domain.entity.user.UserInfo
import com.petsvote.domain.usecases.rating.GetRatingUseCase
import com.petsvote.domain.usecases.user.RegisterUserUseCase
import com.petsvote.domain.usecases.user.SaveUserToLocalUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RatingViewModel @Inject constructor(
    private val ratingUseCase: GetRatingUseCase
) : BaseViewModel() {

    var pages = MutableStateFlow<PagingData<Item>?>(null)

    suspend fun getRating() = withContext(Dispatchers.Default){
        ratingUseCase.getRating().cachedIn(viewModelScope).collect{
            pages.emit(it)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val ratingUseCase: GetRatingUseCase
    )
        : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == RatingViewModel::class.java)
            return RatingViewModel(ratingUseCase = ratingUseCase) as T
        }

    }
}