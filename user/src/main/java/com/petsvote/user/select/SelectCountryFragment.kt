package com.petsvote.user.select

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.petsvote.core.BaseFragment
import com.petsvote.core.adapter.FingerprintListAdapter
import com.petsvote.core.adapter.FingerprintPagingAdapter
import com.petsvote.core.adapter.Item
import com.petsvote.domain.entity.breed.Breed
import com.petsvote.domain.entity.user.location.Country
import com.petsvote.ui.SearchBar
import com.petsvote.ui.hideKeyboard
import com.petsvote.user.R
import com.petsvote.user.UserProfileViewModel
import com.petsvote.user.databinding.FragmentSelectCountryBinding
import com.petsvote.user.di.UserComponentViewModel
import dagger.Lazy
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectCountryFragment: BaseFragment(R.layout.fragment_select_country) {

    @Inject
    internal lateinit var viewModelFactory: Lazy<SelectCountryViewModel.Factory>

    private val userComponentViewModel: UserComponentViewModel by viewModels()
    private val viewModel: SelectCountryViewModel by viewModels {
        viewModelFactory.get()
    }

    private var binding: FragmentSelectCountryBinding? = null
    private var countryAdapter = FingerprintListAdapter(listOf(CountryFingerprint(::onSelectCountry)))
    private var allListCountries = mutableListOf<Item>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSelectCountryBinding.bind(view)
        initList()
        initSearch()

        binding?.back?.setOnClickListener {
            findNavController().popBackStack()
        }

        object : CountDownTimer(500, 500) {

            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                lifecycleScope.launch { viewModel.getCountries() }
            }
        }.start()

    }

    private fun initSearch() {
        binding?.searchBar?.setOnTextSearchBar(object : SearchBar.OnTextSearchBar{
            override fun onText(text: String) {
                var filter = allListCountries.filter { (it as Country).title.contains(text) }
                countryAdapter.submitList(filter)
            }

            override fun onClear() {
                countryAdapter.submitList(allListCountries)
                hideKeyboard()
            }

        })
    }

    private fun initList() {

        binding?.list?.apply {
            itemAnimator = null
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = countryAdapter
        }

    }

    override fun initObservers() {

        lifecycleScope.launchWhenStarted {
            viewModel.countries.collect {
                if(it.isNotEmpty()){
                    countryAdapter.submitList(it)
                    allListCountries = it.toMutableList()
                    binding?.selectBreedsProgress?.visibility = View.GONE
                }
            }
        }

    }

    private fun onSelectCountry(country: Country) {
        viewModel.setCountry(country)
        findNavController().popBackStack()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        userComponentViewModel.userComponent.injectSelectCountry(this)
    }
}