package com.petswote.pet.find

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.petsvote.core.BaseFragment
import com.petswote.pet.R
import com.petswote.pet.add.AddPetViewModel
import com.petswote.pet.databinding.FragmentFindPetBinding
import com.petswote.pet.di.PetComponentViewModel
import dagger.Lazy
import javax.inject.Inject

class FindPetFragment: BaseFragment(R.layout.fragment_find_pet) {

    @Inject
    internal lateinit var viewModelFactory: Lazy<FindPetViewModel.Factory>

    private val petComponentViewModel: PetComponentViewModel by viewModels()
    private val viewModel: FindPetViewModel by viewModels {
        viewModelFactory.get()
    }

    private var binding: FragmentFindPetBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentFindPetBinding.bind(view)

        binding?.findContainer?.setOnClickListener {
            viewModel.findPet(20959902)
        }
    }

    override fun initObservers() {

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        petComponentViewModel.petComponent.injectFindPet(this)
    }
}