package com.petsvote.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.petsvote.core.BaseViewModel
import com.petsvote.core.adapter.Item
import com.petsvote.core.ext.log
import com.petsvote.domain.entity.breed.Breed
import com.petsvote.domain.usecases.configuration.IGetBreedsUseCase
import com.petsvote.domain.usecases.filter.*
import com.petsvote.domain.usecases.filter.impl.GetFilterUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FilterViewModel @Inject constructor(
    private val filterUseCase: IGetFilterUseCase,
    private val setSexFilterUseCase: ISetSexUseCase,
    private val setMaxAgeUseCase: ISetMaxAgeUseCase,
    private val setMinAgeUseCase: ISetMinAgeUseCase,
    private val setDefaultRatingFilter: ISetDefaultRatingFilterUseCase,
    private val getKindsUseCase: IGetKindsUseCase
) : BaseViewModel() {

    var kind = MutableStateFlow<String>("")
    var breed = MutableStateFlow<String>("")
    var ageMin = MutableStateFlow<String>("")
    var ageMax = MutableStateFlow<String>("")
    var min = MutableStateFlow<Int>(0)
    var max = MutableStateFlow<Int>(200)
    var sex = MutableStateFlow<Int>(-1)
    var isBreedRight = MutableStateFlow<Boolean>(false)
    var topSelect = MutableStateFlow<Int?>(-2)

    fun getFilter() {

        viewModelScope.launch {
            filterUseCase.getFilter().collect {
                log("filter emit ${it.filterMax}")
                max.emit(it.filterMax)
                min.emit(0)
                kind.emit(it.kind)
                ageMin.emit(it.ageMin)
                ageMax.emit(it.ageMax)
                isBreedRight.emit(it.isBreedRight)
                breed.emit(it.breed)
            }
        }
    }

    fun getSex(){
        viewModelScope.launch {
            sex.emit(filterUseCase.getFilter().first().sex)
        }
    }

    fun setMax(max: Int){
        viewModelScope.launch (Dispatchers.IO){ setMaxAgeUseCase.setMax(max)}
    }

    fun setMin(min: Int){
        viewModelScope.launch (Dispatchers.IO){ setMinAgeUseCase.setMin(min)}
    }

    fun setSex(tabSex: Int){
        viewModelScope.launch {
            setSexFilterUseCase.setSex(tabSex)
        }
    }

    fun resetFilter(){
        viewModelScope.launch (Dispatchers.IO){ setDefaultRatingFilter.setDefaultRatingFilter() }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val filterUseCase: IGetFilterUseCase,
        private val setSexFilterUseCase: ISetSexUseCase,
        private val setMaxAgeUseCase: ISetMaxAgeUseCase,
        private val setMinAgeUseCase: ISetMinAgeUseCase,
        private val setDefaultRatingFilter: ISetDefaultRatingFilterUseCase,
        private val getKindsUseCase: IGetKindsUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == FilterViewModel::class.java)
            return FilterViewModel(
                filterUseCase = filterUseCase,
                setSexFilterUseCase = setSexFilterUseCase,
                setMaxAgeUseCase = setMaxAgeUseCase,
                setMinAgeUseCase = setMinAgeUseCase,
                setDefaultRatingFilter = setDefaultRatingFilter,
                getKindsUseCase = getKindsUseCase
            ) as T
        }

    }
}
