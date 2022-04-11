package com.petsvote.legal.main

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.petsvote.core.BaseFragment
import com.petsvote.legal.R
import com.petsvote.legal.databinding.FragmentTermsBinding
import com.petsvote.legal.di.TermsComponentViewModel
import com.petsvote.navigation.MainNavigation
import dagger.Lazy
import kotlinx.coroutines.flow.collect
import me.vponomarenko.injectionmanager.x.XInjectionManager
import javax.inject.Inject

open class TermsFragment: BaseFragment(R.layout.fragment_terms) {

    companion object {
        const val TERMS_KEY = "data"
        const val TERMS_ID = "id"
    }

    private val navigation: MainNavigation by lazy {
        XInjectionManager.findComponent<MainNavigation>()
    }

    @Inject
    internal lateinit var termsViewModelFactory: Lazy<TermsViewModel.Factory>

    private val termsComponentViewModel: TermsComponentViewModel by viewModels()
    private val viewModel: TermsViewModel by viewModels {
        termsViewModelFactory.get()
    }

    private var binding: FragmentTermsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = FragmentTermsBinding.bind(view)
        initViews()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViews() {

        binding?.close?.setOnClickListener {
            navigation.back()
        }

    }

    override fun initObservers() {

        lifecycleScope.launchWhenStarted { viewModel.loadData() }

        lifecycleScope.launchWhenStarted {
           viewModel._dataPrivacyPolicy.collect {
               if(it.isNotEmpty()) {
                   binding?.containerPolicy?.setOnClickListener { view ->
                       view.isPressed = true
                       navigation.startInfoTerms(bundleOf(TERMS_KEY to it, TERMS_ID to 1))
                   }

               }
           }
        }

        lifecycleScope.launchWhenStarted {
            viewModel._dataUserAgreement.collect {
                if(it.isNotEmpty()) {
                    binding?.containerTerms?.setOnClickListener { view ->
                        view.isPressed = true
                        navigation.startInfoTerms(bundleOf(TERMS_KEY to it, TERMS_ID to 2))
                    }
                }
            }
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        termsComponentViewModel.termsComponent.inject(this)
    }

}