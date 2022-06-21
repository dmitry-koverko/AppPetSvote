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
import com.petsvote.core.adapter.Item
import com.petsvote.domain.entity.user.location.City
import com.petsvote.domain.entity.user.location.Country
import com.petsvote.ui.SearchBar
import com.petsvote.ui.hideKeyboard
import com.petsvote.user.R
import com.petsvote.user.databinding.FragmentSelectCityBinding
import com.petsvote.user.databinding.FragmentSelectCountryBinding
import com.petsvote.user.di.UserComponentViewModel
import dagger.Lazy
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectCityFragment: BaseFragment(R.layout.fragment_select_city) {

    @Inject
    internal lateinit var viewModelFactory: Lazy<SelectCityViewModel.Factory>

    private val userComponentViewModel: UserComponentViewModel by viewModels()
    private val viewModel: SelectCityViewModel by viewModels {
        viewModelFactory.get()
    }

    private var binding: FragmentSelectCityBinding? = null
    private var citiesAdapter = FingerprintListAdapter(listOf(CityFingerprint(::onSelectCity)))
    private var allListCities = mutableListOf<Item>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSelectCityBinding.bind(view)
        initList()
        initSearch()

        binding?.back?.setOnClickListener {
            findNavController().popBackStack()
        }

        object : CountDownTimer(500, 500) {

            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                lifecycleScope.launch { viewModel.getCities() }
            }
        }.start()

    }

    private fun initSearch() {
        binding?.searchBar?.setOnTextSearchBar(object : SearchBar.OnTextSearchBar{
            override fun onText(text: String) {
                var filter = allListCities.filter { (it as City).title.contains(text) or ((it as City).region?.contains(text) == true) }
                citiesAdapter.submitList(filter)
            }

            override fun onClear() {
                citiesAdapter.submitList(allListCities)
                hideKeyboard()
            }

        })
    }

    private fun initList() {

        binding?.list?.apply {
            itemAnimator = null
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = citiesAdapter
        }

    }

    override fun initObservers() {

        lifecycleScope.launchWhenStarted {
            viewModel.cities.collect {
                if(it.isNotEmpty()){
                    citiesAdapter.submitList(it)
                    allListCities = it.toMutableList()
                    binding?.selectCityProgress?.visibility = View.GONE
                }
            }
        }

    }

    private fun onSelectCity(city: City) {
        viewModel.setCity(city)
        findNavController().popBackStack()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        userComponentViewModel.userComponent.injectSelectCity(this)
    }
}