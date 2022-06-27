package com.petsvote.filter.kinds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.petsvote.core.BaseViewModel
import com.petsvote.core.adapter.Item
import com.petsvote.domain.entity.filter.Kind
import com.petsvote.domain.usecases.filter.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SelectKindsVewModel @Inject constructor(
    private val kindsUseCase: IGetKindsUseCase,
    private val setKindsUseCase: ISetKindsRatingFilterUseCase,
    private val setBreedsUseCase: ISetBreedUseCase,
) : BaseViewModel() {

    var kinds = MutableStateFlow<List<Kind>>(emptyList())
    private var filterKinds = MutableStateFlow<String?>(null)


    suspend fun getKinds() = withContext(Dispatchers.IO) {
        kinds.emit(kindsUseCase.getKinds(0))
    }

    fun setKindsFilter(list: List<Item>) {
        viewModelScope.launch {
            setKindsUseCase.setKinds(list)
            setBreedsUseCase.setBreedFilter(null)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val kindsUseCase: IGetKindsUseCase,
        private val setKindsUseCase: ISetKindsRatingFilterUseCase,
        private val setBreedsUseCase: ISetBreedUseCase,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == SelectKindsVewModel::class.java)
            return SelectKindsVewModel(
                kindsUseCase = kindsUseCase,
                setKindsUseCase = setKindsUseCase,
                setBreedsUseCase = setBreedsUseCase
            ) as T
        }

    }
}