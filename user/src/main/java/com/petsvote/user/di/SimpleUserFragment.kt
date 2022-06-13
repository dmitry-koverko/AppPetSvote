package com.petsvote.user.di

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.petsvote.core.BaseFragment
import com.petsvote.core.adapter.FingerprintListAdapter
import com.petsvote.core.adapter.FingerprintPagingAdapter
import com.petsvote.domain.entity.user.UserPet
import com.petsvote.ui.loadImage
import com.petsvote.user.R
import com.petsvote.user.databinding.FragmentSimpleUserBinding
import dagger.Lazy
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class SimpleUserFragment: BaseFragment(R.layout.fragment_simple_user) {

    @Inject
    internal lateinit var viewModelFactory: Lazy<SimpleUserViewModel.Factory>

    private val userComponentViewModel: UserComponentViewModel by viewModels()
    private val viewModel: SimpleUserViewModel by viewModels {
        viewModelFactory.get()
    }

    private var petsAdapter = FingerprintListAdapter(listOf(UserPetsFingerprint(::clickPet)))

    private var binding: FragmentSimpleUserBinding? = null
    private var settingsDialog = SettingProfileFragment()

    override fun initObservers() {
        lifecycleScope.launchWhenResumed {
            viewModel.pets.collect {
                petsAdapter.submitList(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.ava.collect {
                binding?.userAva?.loadImage(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSimpleUserBinding.bind(view)
        binding?.addPet?.animation = true
        binding?.userPets?.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = petsAdapter
        }

        binding?.profileContainer?.setOnClickListener {
            try {
                if(!settingsDialog.isAdded){
                    settingsDialog.show(childFragmentManager, "settingsDialog")
                }
            }catch (e: Exception){}
        }

        viewModel.getPets()
    }

    private fun clickPet(pet: UserPet){

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        userComponentViewModel.userComponent.inject(this)
    }

}