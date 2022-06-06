package com.petsvote.filter.breeds

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.petsvote.core.BaseFragment
import com.petsvote.core.adapter.FingerprintListAdapter
import com.petsvote.core.adapter.FingerprintPagingAdapter
import com.petsvote.core.adapter.Item
import com.petsvote.domain.entity.breed.Breed
import com.petsvote.filter.R
import com.petsvote.filter.databinding.FragmentSelectBreedsBinding
import com.petsvote.filter.di.FilterComponentViewModel
import com.petsvote.ui.SearchBar
import com.petsvote.ui.hideKeyboard
import dagger.Lazy
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectBreedsFragment: BaseFragment(R.layout.fragment_select_breeds),
    SearchBar.OnTextSearchBar {

    @Inject
    internal lateinit var viewModelFactory: Lazy<SelectBreedsViewModel.Factory>

    private val filterComponentViewModel: FilterComponentViewModel by viewModels()
    private val viewModel: SelectBreedsViewModel by viewModels {
        viewModelFactory.get()
    }

    private var breeds = mutableListOf<Item>()
    private val breedsAdapter = FingerprintListAdapter(listOf(BreedFingerprint(::onSelectKind)))
    private var breedsPagingAdapter = FingerprintPagingAdapter(listOf(BreedFingerprint(::onSelectKind)))

    private var binding: FragmentSelectBreedsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSelectBreedsBinding.bind(view)
        initList()
        initBack()
        initSearch()
        lifecycleScope.launchWhenStarted { viewModel.getBreedsPaging() }
    }

    private fun initSearch() {
        binding?.searchBar?.setOnTextSearchBar(this)
    }

    private fun initBack() {
        binding?.back?.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initList() {

        binding?.list?.apply {
            itemAnimator = null
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = breedsPagingAdapter
        }

    }

    override fun initObservers() {

        lifecycleScope.launchWhenStarted {
            viewModel.isSelect.collect {
                if(it) findNavController().popBackStack()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.pages.collect {
                it?.let { it1 -> breedsPagingAdapter.submitData(it1) }
            }
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        filterComponentViewModel.filterComponent.injectSelectBreeds(this)
    }

    private fun onSelectKind(breed: Breed){
        lifecycleScope.launch {
            viewModel.setBreedFilter(breed)
        }

    }

    override fun onText(text: String) {
        //binding?.list?.post { breedsAdapter.submitList(breeds.filter { (it as Breed).breedName.lowercase().contains(text.lowercase()) }) }
        viewModel.setTextFilter(text)
        lifecycleScope.launch {
            breedsPagingAdapter.submitData(PagingData.empty()) }
    }

    override fun onClear() {
        viewModel.clearFilter()
        hideKeyboard()
    }

}