package com.petsvote.filter

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.petsvote.core.BaseFragment
import com.petsvote.core.adapter.FingerprintListAdapter
import com.petsvote.core.adapter.Item
import com.petsvote.domain.entity.filter.Kind
import com.petsvote.filter.databinding.FragmentSelectKindsBinding
import com.petsvote.filter.di.FilterComponentViewModel
import dagger.Lazy
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class SelectKindsFragment : BaseFragment(R.layout.fragment_select_kinds) {

    @Inject
    internal lateinit var viewModelFactory: Lazy<SelectKindsVewModel.Factory>

    private val filterComponentViewModel: FilterComponentViewModel by viewModels()
    private val viewModel: SelectKindsVewModel by viewModels {
        viewModelFactory.get()
    }

    private var kinds = mutableListOf<Item>()
    private val kindsAdapter = FingerprintListAdapter(listOf(KindsFingerprint(::onSelectKind)))

    private var allKindCheck = true

    private var binding: FragmentSelectKindsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSelectKindsBinding.bind(view)

        initList()
        initRbtn()
        initHome()
        lifecycleScope.launchWhenStarted { viewModel.getKinds() }
    }

    private fun initHome() {
        binding?.back?.setOnClickListener {

        }
    }

    private fun initRbtn() {
        binding?.rbtn?.setOnClickListener {
            if (!allKindCheck) checkAllRbtn()
            else checkCatsRbtn()
            allKindCheck = !allKindCheck
            binding?.rbtn?.isChecked = allKindCheck
            kindsAdapter.notifyDataSetChanged()
            viewModel.setKindsFilter(kinds)
        }
    }

    private fun checkCatsRbtn() {

        for (kind in kinds) {
            (kind as Kind).isSelect = false
        }
        (kinds.find { (it as Kind).id == 0 } as Kind).isSelect = true

    }

    private fun checkAllRbtn() {
        for (kind in kinds) {
            (kind as Kind).isSelect = true
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
                kinds = it.toMutableList()
                kindsAdapter.submitList(kinds)
                if(kinds.filter { !(it as Kind).isSelect }.isNotEmpty()){
                    allKindCheck = false
                    binding?.rbtn?.isChecked = false
                }
            }
        }

    }

    private fun onSelectKind(kind: Kind) {
        var listSelect = kinds.filter { (it as Kind).isSelect }
        if (kind.isSelect && listSelect.size == 1) return
        (kinds.find { (it as Kind).id == kind.id } as Kind).isSelect =
            !(kind.isSelect && listSelect.isNotEmpty())
        binding?.rbtn?.isChecked = kinds.filter { (it as Kind).isSelect }.size == kinds.size
        allKindCheck = binding?.rbtn?.isChecked == true
        kindsAdapter.notifyItemChanged(kinds.indexOf(kind))
        viewModel.setKindsFilter(kinds)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        filterComponentViewModel.filterComponent.injectSelectKinds(this)
    }
}