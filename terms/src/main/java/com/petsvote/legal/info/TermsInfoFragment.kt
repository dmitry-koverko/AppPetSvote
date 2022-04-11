package com.petsvote.legal.info

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.petsvote.core.BaseFragment
import com.petsvote.legal.R
import com.petsvote.legal.databinding.FragmentTermsInfoBinding
import com.petsvote.legal.di.TermsComponentViewModel
import com.petsvote.navigation.MainNavigation
import dagger.Lazy
import kotlinx.coroutines.flow.collect
import me.vponomarenko.injectionmanager.x.XInjectionManager
import javax.inject.Inject

class TermsInfoFragment: BaseFragment(R.layout.fragment_terms_info) {

    private var binding: FragmentTermsInfoBinding? = null

    private val navigation: MainNavigation by lazy {
        XInjectionManager.findComponent<MainNavigation>()
    }

    @Inject
    internal lateinit var termsInfoViewModelFactory: Lazy<TermsInfoViewModel.Factory>

    private val termsComponentViewModel: TermsComponentViewModel by viewModels()
    private val viewModel: TermsInfoViewModel by viewModels {
        termsInfoViewModelFactory.get()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentTermsInfoBinding.bind(view)

        super.onViewCreated(view, savedInstanceState)
    }

    override fun initObservers() {

        lifecycleScope.launchWhenStarted {
            viewModel._dataDescription.collect {
                binding?.expandedImage?.text = it
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel._dataTitle.collect {
                binding?.text?.text = it
            }
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        termsComponentViewModel.termsComponent.injectTermsInfo(this)
    }

}