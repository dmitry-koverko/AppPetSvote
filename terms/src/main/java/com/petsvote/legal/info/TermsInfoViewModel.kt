package com.petsvote.legal.info

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.petsvote.core.BaseViewModel
import com.petsvote.domain.usecases.resources.GetStringResourcesUseCase
import com.petsvote.legal.main.TermsFragment
import com.petsvote.legal.main.TermsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TermsInfoViewModel @Inject constructor(
    private val getStringResourcesUseCase: GetStringResourcesUseCase
) : BaseViewModel() {

    private var dataTitle = MutableStateFlow<String>("")
    var _dataTitle = dataTitle.asStateFlow()

    private var dataDescription = MutableStateFlow<String>("")
    var _dataDescription = dataDescription.asStateFlow()

    suspend fun loadData(bundle: Bundle) = withContext(Dispatchers.IO) {

        dataTitle.value =
            if (bundle.getInt(TermsFragment.TERMS_ID) == 1)
                getStringResourcesUseCase.getString(com.petsvote.ui.R.string.policy_text)
            else getStringResourcesUseCase.getString(com.petsvote.ui.R.string.terms_text)
        dataDescription.value = bundle.getString(TermsFragment.TERMS_KEY, "")

    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(private val getStringResourcesUseCase: GetStringResourcesUseCase) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == TermsInfoViewModel::class.java)
            return TermsInfoViewModel(getStringResourcesUseCase = getStringResourcesUseCase) as T
        }

    }
}
