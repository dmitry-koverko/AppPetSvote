package com.petsvote.filter

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
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

class SelectKindsFragment: BaseFragment(R.layout.fragment_select_kinds) {

    @Inject
    internal lateinit var viewModelFactory: Lazy<SelectKindsVewModel.Factory>

    private val filterComponentViewModel: FilterComponentViewModel by viewModels()
    private val viewModel: SelectKindsVewModel by viewModels {
        viewModelFactory.get()
    }

    private var kinds = mutableListOf<Item>()
    private val kindsAdapter = FingerprintListAdapter(listOf(KindsFingerprint(::onSelectKind)))

    private var binding: FragmentSelectKindsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSelectKindsBinding.bind(view)

        initList()
        viewModel.getKinds()
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
            }
        }

    }

    private fun onSelectKind(kind: Kind){

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        filterComponentViewModel.filterComponent.injectSelectKinds(this)
    }
}