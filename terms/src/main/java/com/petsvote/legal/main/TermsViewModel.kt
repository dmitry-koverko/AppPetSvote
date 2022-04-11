package com.petsvote.legal.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.petsvote.core.BaseViewModel
import com.petsvote.domain.usecases.configuration.GetPrivacyPolicyUseCase
import com.petsvote.domain.usecases.configuration.GetUserAgreementUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TermsViewModel @Inject constructor(
    private val getPrivacyPolicyUseCase: GetPrivacyPolicyUseCase,
    private val getUserAgreementUseCase: GetUserAgreementUseCase
) : BaseViewModel() {

    private var dataPrivacyPolicy = MutableStateFlow<String>("")
    var _dataPrivacyPolicy = dataPrivacyPolicy.asStateFlow()

    private var dataUserAgreement = MutableStateFlow<String>("")
    var _dataUserAgreement = dataUserAgreement.asStateFlow()

    suspend fun loadData() = withContext(Dispatchers.IO) {

        launch {
            dataPrivacyPolicy.value = getPrivacyPolicyUseCase.getPrivacyPolicy()
        }

        launch {
            dataUserAgreement.value = getUserAgreementUseCase.getUserAgreement()
        }


    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val getPrivacyPolicyUseCase: GetPrivacyPolicyUseCase,
        private val getUserAgreementUseCase: GetUserAgreementUseCase)
        : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == TermsViewModel::class.java)
            return TermsViewModel(
                getPrivacyPolicyUseCase = getPrivacyPolicyUseCase,
                getUserAgreementUseCase = getUserAgreementUseCase
            ) as T
        }

    }
}
