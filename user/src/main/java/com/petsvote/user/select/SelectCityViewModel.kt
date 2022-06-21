package com.petsvote.user.select

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.petsvote.core.BaseViewModel
import com.petsvote.domain.entity.user.location.City
import com.petsvote.domain.entity.user.location.Country
import com.petsvote.domain.usecases.user.IGetCitiesListUseCase
import com.petsvote.domain.usecases.user.IGetCountryListUseCase
import com.petsvote.domain.usecases.user.ISetCityUseCase
import com.petsvote.domain.usecases.user.ISetCountryUseCase
import com.petsvote.domain.usecases.user.impl.GetCitiesListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectCityViewModel @Inject constructor(
    private val getCitiesListUseCase: IGetCitiesListUseCase,
    private val setCityUseCase: ISetCityUseCase
) : BaseViewModel() {

    var cities = MutableStateFlow<List<City>>(listOf())

    fun getCities() {

        viewModelScope.launch(Dispatchers.IO) {
            cities.emit(getCitiesListUseCase.getCities())
        }
    }

    fun setCity(city: City){
        viewModelScope.launch {
            setCityUseCase.setCity(city)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val getCitiesListUseCase: IGetCitiesListUseCase,
        private val setCityUseCase: ISetCityUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == SelectCityViewModel::class.java)
            return SelectCityViewModel(
                getCitiesListUseCase = getCitiesListUseCase,
                setCityUseCase = setCityUseCase
            ) as T
        }

    }

}