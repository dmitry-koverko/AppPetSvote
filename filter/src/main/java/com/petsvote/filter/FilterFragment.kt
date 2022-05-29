package com.petsvote.filter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.petsvote.filter.databinding.FragmentFilterBinding
import com.petsvote.navigation.MainNavigation
import me.vponomarenko.injectionmanager.x.XInjectionManager

class FilterFragment: Fragment(R.layout.fragment_filter) {

    private val navigation: MainNavigation by lazy {
        XInjectionManager.findComponent<MainNavigation>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var binding = FragmentFilterBinding.bind(view)

        binding.containerKids.setOnClickListener {
            navigation.startSelectKinds()
        }

        binding.containerBreeds.setOnClickListener {
            navigation.startSelectBreeds()
        }
    }

}