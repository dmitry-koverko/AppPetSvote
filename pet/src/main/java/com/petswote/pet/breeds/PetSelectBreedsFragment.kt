package com.petswote.pet.breeds

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.petsvote.core.BaseFragment
import com.petsvote.core.adapter.FingerprintPagingAdapter
import com.petsvote.domain.entity.breed.Breed
import com.petsvote.ui.SearchBar
import com.petsvote.ui.hideKeyboard
import com.petswote.pet.R
import com.petswote.pet.databinding.FragmentPetSelectBreedsBinding
import com.petswote.pet.di.PetComponentViewModel
import com.petswote.pet.kinds.PetSelectKindsViewModel
import dagger.Lazy
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class PetSelectBreedsFragment: BaseFragment(R.layout.fragment_pet_select_breeds),
    SearchBar.OnTextSearchBar {

    @Inject
    internal lateinit var viewModelFactory: Lazy<PetSelectBreedsViewModel.Factory>

    private val petComponentViewModel: PetComponentViewModel by viewModels()
    private val viewModel: PetSelectBreedsViewModel by viewModels {
        viewModelFactory.get()
    }

    private var binding: FragmentPetSelectBreedsBinding? = null

    private var breedsPagingAdapter = FingerprintPagingAdapter(listOf(PetBreedFingerprint(::onSelectKind)))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPetSelectBreedsBinding.bind(view)

        initList()
        initBack()
        initSearch()

        object : CountDownTimer(500, 500) {

            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                lifecycleScope.launch { viewModel.getBreedsPaging() }
            }
        }.start()
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
            viewModel.pages.collect {
                it?.let { it1 ->
                    breedsPagingAdapter.submitData(it1)
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.progress.collect {
                if(!it) binding?.selectBreedsProgress?.visibility = View.GONE
            }
        }
    }

    private fun onSelectKind(breed: Breed){

        lifecycleScope.launch {
            viewModel.setBreedFilter(breed)
        }
        findNavController().popBackStack()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        petComponentViewModel.petComponent.injectSelectBreeds(this)
    }

    override fun onText(text: String) {
        viewModel.setTextFilter(text)
        lifecycleScope.launch {
            breedsPagingAdapter.submitData(PagingData.empty()) }
    }

    override fun onClear() {
        viewModel.clearFilter()
        hideKeyboard()
    }
}