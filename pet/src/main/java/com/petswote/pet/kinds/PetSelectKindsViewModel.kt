package com.petswote.pet.kinds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.petsvote.core.BaseViewModel
import com.petsvote.core.adapter.Item
import com.petsvote.domain.entity.filter.Kind
import com.petsvote.domain.usecases.filter.IGetKindsUseCase
import com.petsvote.domain.usecases.filter.ISetBreedUseCase
import com.petsvote.domain.usecases.filter.ISetKindsRatingFilterUseCase
import com.petsvote.domain.usecases.pet.create.ISetKindPetUseCase
import com.petsvote.domain.usecases.pet.create.impl.SetKindPetUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PetSelectKindsViewModel @Inject constructor(
    private val kindsUseCase: IGetKindsUseCase,
    private val setKindPetUseCase: ISetKindPetUseCase
) : BaseViewModel() {

    var kinds = MutableStateFlow<List<Kind>>(emptyList())


    suspend fun getKinds() = withContext(Dispatchers.IO) {
        kinds.emit(kindsUseCase.getKinds(1))
    }

    fun setKindsFilter(item: Item) {
        viewModelScope.launch (Dispatchers.IO) {
            setKindPetUseCase.setKind((item as Kind).id, (item as Kind).title)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val kindsUseCase: IGetKindsUseCase,
        private val setKindPetUseCase: ISetKindPetUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == PetSelectKindsViewModel::class.java)
            return PetSelectKindsViewModel(
                kindsUseCase = kindsUseCase,
                setKindPetUseCase = setKindPetUseCase
            ) as T
        }

    }
}