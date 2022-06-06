package com.petsvote.filter

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.petsvote.core.BaseFragment
import com.petsvote.filter.databinding.FragmentFilterBinding
import com.petsvote.filter.di.FilterComponentViewModel
import com.petsvote.filter.kinds.SelectKindsVewModel
import com.petsvote.navigation.MainNavigation
import com.petsvote.ui.maintabs.BesieTabLayoutSelectedListener
import com.petsvote.ui.maintabs.BesieTabSelected
import dagger.Lazy
import kotlinx.coroutines.flow.collect
import me.vponomarenko.injectionmanager.x.XInjectionManager
import javax.inject.Inject

class FilterFragment: BaseFragment(R.layout.fragment_filter), BesieTabLayoutSelectedListener {

    private val navigation: MainNavigation by lazy {
        XInjectionManager.findComponent<MainNavigation>()
    }

    @Inject
    internal lateinit var viewModelFactory: Lazy<FilterViewModel.Factory>

    private val filterComponentViewModel: FilterComponentViewModel by viewModels()
    private val viewModel: FilterViewModel by viewModels {
        viewModelFactory.get()
    }

    private var binding: FragmentFilterBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentFilterBinding.bind(view)

        binding?.containerKids?.setOnClickListener {
            navigation.startSelectKinds()
        }

        binding?.containerBreeds?.setOnClickListener {
            navigation.startSelectBreeds()
        }

        initTabs()
        viewModel.getFilter()
    }

    private fun initTabs() {
        binding?.tabs?.setTabSelectedListener(this)
    }

    override fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.sex.collect {
                when(it){
                    1 -> binding?.tabs?.initCountryTabs()
                    2 -> binding?.tabs?.initWorldTab()
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.kind.collect {
                binding?.kids?.text = it
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        filterComponentViewModel.filterComponent.inject(this)
    }

    override fun selected(tab: BesieTabSelected) {
        var index = when(tab){
            BesieTabSelected.ALL -> 0
            BesieTabSelected.MAN -> 1
            BesieTabSelected.GIRLS -> 2
            else -> 0
        }
        viewModel.setSex(index)
    }

}