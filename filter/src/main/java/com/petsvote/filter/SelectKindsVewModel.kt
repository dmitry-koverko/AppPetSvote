package com.petsvote.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.petsvote.core.BaseViewModel
import com.petsvote.core.adapter.Item
import com.petsvote.core.ext.log
import com.petsvote.domain.entity.filter.Kind
import com.petsvote.domain.entity.filter.RatingFilterType
import com.petsvote.domain.entity.user.UserPet
import com.petsvote.domain.usecases.filter.*
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

class SelectKindsVewModel @Inject constructor(
    private val kindsUseCase: IGetKindsUseCase
) : BaseViewModel() {

    var kinds = MutableStateFlow<List<Kind>>(emptyList())

    fun getKinds(){
        viewModelScope.launch {
            kinds.emit(kindsUseCase.getKinds())
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val kindsUseCase: IGetKindsUseCase
    )
        : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == SelectKindsVewModel::class.java)
            return SelectKindsVewModel(
                kindsUseCase = kindsUseCase) as T
        }

    }
}