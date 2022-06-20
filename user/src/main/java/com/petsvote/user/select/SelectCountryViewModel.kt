package com.petsvote.user.select

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.petsvote.core.BaseViewModel
import com.petsvote.domain.entity.user.location.Country
import com.petsvote.domain.usecases.configuration.IGetAddPhotosSettingsUseCase
import com.petsvote.domain.usecases.configuration.IGetImagesUseCase
import com.petsvote.domain.usecases.configuration.ISetAddPhotosSettingsUseCase
import com.petsvote.domain.usecases.configuration.ISetImageCropUseCase
import com.petsvote.domain.usecases.user.IGetCountryListUseCase
import com.petsvote.domain.usecases.user.IGetUserUseCase
import com.petsvote.domain.usecases.user.ISetCountryUseCase
import com.petsvote.domain.usecases.user.impl.SetCountryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import javax.inject.Inject

class SelectCountryViewModel @Inject constructor(
    private val getCountryUseCase: IGetCountryListUseCase,
    private val setCountryUseCase: ISetCountryUseCase
) : BaseViewModel() {

    var countries = MutableStateFlow<List<Country>>(listOf())

    fun getCountries() {

        viewModelScope.launch(Dispatchers.IO) {
            countries.emit(getCountryUseCase.getCountries())
        }
    }

    fun setCountry(country: Country){
        viewModelScope.launch {
            setCountryUseCase.setCountry(country)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val getCountryUseCase: IGetCountryListUseCase,
        private val setCountryUseCase: ISetCountryUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == SelectCountryViewModel::class.java)
            return SelectCountryViewModel(
                getCountryUseCase = getCountryUseCase,
                setCountryUseCase = setCountryUseCase
            ) as T
        }

    }

}