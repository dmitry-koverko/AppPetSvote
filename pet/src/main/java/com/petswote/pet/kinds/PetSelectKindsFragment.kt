package com.petswote.pet.kinds

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
import com.petsvote.domain.entity.filter.Kind
import com.petswote.pet.R
import com.petswote.pet.add.PetKindsFingerprint
import com.petswote.pet.databinding.FragmentPetSelectKindBinding
import com.petswote.pet.di.PetComponentViewModel
import dagger.Lazy
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class PetSelectKindsFragment: BaseFragment(R.layout.fragment_pet_select_kind) {

    @Inject
    internal lateinit var viewModelFactory: Lazy<PetSelectKindsViewModel.Factory>

    private val petComponentViewModel: PetComponentViewModel by viewModels()
    private val viewModel: PetSelectKindsViewModel by viewModels {
        viewModelFactory.get()
    }

    private val kindsAdapter = FingerprintListAdapter(listOf(PetKindsFingerprint(::onSelectKind)))

    private var binding: FragmentPetSelectKindBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPetSelectKindBinding.bind(view)

        initList()
        initHome()

        lifecycleScope.launch { viewModel.getKinds() }
    }

    private fun initHome() {
        binding?.back?.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initList() {
        binding?.list?.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = kindsAdapter
        }
    }

    override fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.kinds.collect {
                kindsAdapter.submitList(it)
            }
        }
    }

    private fun onSelectKind(kind: Kind) {
        viewModel.setKindsFilter(kind)
        findNavController().popBackStack()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        petComponentViewModel.petComponent.injectSelectKinds(this)
    }
}