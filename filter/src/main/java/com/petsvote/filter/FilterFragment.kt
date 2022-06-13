package com.petsvote.filter

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.petsvote.core.BaseFragment
import com.petsvote.filter.databinding.FragmentFilterBinding
import com.petsvote.filter.di.FilterComponentViewModel
import com.petsvote.filter.kinds.SelectKindsVewModel
import com.petsvote.navigation.MainNavigation
import com.petsvote.ui.maintabs.BesieTabLayoutSelectedListener
import com.petsvote.ui.maintabs.BesieTabSelected
import dagger.Lazy
import kotlinx.coroutines.flow.collect
import me.vponomarenko.injectionmanager.x.XInjectionManager
import javax.inject.Inject

class FilterFragment: BaseFragment(R.layout.fragment_filter), BesieTabLayoutSelectedListener {

    private val navigation: MainNavigation by lazy {
        XInjectionManager.findComponent<MainNavigation>()
    }

    @Inject
    internal lateinit var viewModelFactory: Lazy<FilterViewModel.Factory>

    private val filterComponentViewModel: FilterComponentViewModel by viewModels()
    private val viewModel: FilterViewModel by viewModels {
        viewModelFactory.get()
    }

    private var binding: FragmentFilterBinding? = null

    private var max: Int = 0
    private var min: Int = 0

    private var maxCurrent: Int = 0
    private var minCurrent: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentFilterBinding.bind(view)

        binding?.containerKids?.setOnClickListener {
            navigation.startSelectKinds()
        }

        binding?.containerBreeds?.setOnClickListener {
            navigation.startSelectBreeds()
        }

        initTabs()
        viewModel.getFilter()
    }

    private fun initTabs() {
        binding?.tabs?.setTabSelectedListener(this)
        binding?.blRight2?.click = false
        binding?.blLeft1?.click = false
    }

    override fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.sex.collect {
                when(it){
                    1 -> binding?.tabs?.initCountryTabs()
                    2 -> binding?.tabs?.initWorldTab()
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.kind.collect {
                binding?.kids?.text = it
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.breed.collect {
                binding?.breeds?.text = it
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.ageMax.collect {
                binding?.sfMaxValue?.text = it
                if(it.isNotEmpty()) initMax(it.toInt())
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.ageMin.collect {
                binding?.sfMinValue?.text = it
                if(it.isNotEmpty()) initMin(it.toInt())
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.isBreedRight.collect {
               if(it){
                   binding?.containerBreeds?.isClickable = true
                   binding?.rightBreeds?.visibility = View.VISIBLE
               }else{
                   binding?.containerBreeds?.isClickable = false
                   binding?.rightBreeds?.visibility = View.GONE
               }
            }
        }

    }

    private fun checkMaxR(){
        if(maxCurrent == minCurrent) {
            binding?.blRight1?.click = false
            binding?.imgRightAgeM?.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    com.petsvote.ui.R.drawable.ic_minus_svg_disable
                )
            )
        }else {
            binding?.blRight1?.click = true
            binding?.imgRightAgeM?.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    com.petsvote.ui.R.drawable.ic_minus_active
                )
            )
        }
    }

    private fun checkMaxL(){
        if(maxCurrent == max) {
            binding?.blRight2?.click = false
            binding?.imgRightAgeP?.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    com.petsvote.ui.R.drawable.ic_add_disable
                )
            )
        }else {
            binding?.blRight2?.click = true
            binding?.imgRightAgeP?.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    com.petsvote.ui.R.drawable.ic_add_active
                )
            )
        }
    }

    private fun checkMinR(){
        if(min == minCurrent) {
            binding?.blLeft1?.click = false
            binding?.imgLeftAgeM?.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    com.petsvote.ui.R.drawable.ic_minus_svg_disable
                )
            )
        }else {
            binding?.blLeft1?.click = true
            binding?.imgLeftAgeM?.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    com.petsvote.ui.R.drawable.ic_minus_active
                )
            )
        }
    }

    private fun checkMinL(){
        if(minCurrent == maxCurrent) {
            binding?.blLeft2?.click = false
            binding?.imgLeftAgeP?.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    com.petsvote.ui.R.drawable.ic_add_disable
                )
            )
        }else {
            binding?.blLeft2?.click = true
            binding?.imgLeftAgeP?.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    com.petsvote.ui.R.drawable.ic_add_active
                )
            )
        }
    }

    private fun initMax(maxValue: Int){
        max = maxValue
        maxCurrent = maxValue
        binding?.blRight1?.setOnClickListener {
            maxCurrent -= 1
            binding?.sfMaxValue?.text = maxCurrent.toString()
            checkMaxL()
            checkMaxR()

        }
        binding?.blRight2?.setOnClickListener {
            maxCurrent += 1
            binding?.sfMaxValue?.text = maxCurrent.toString()
            checkMaxR()
            checkMaxL()
        }
    }

    private fun initMin(minValue: Int){
        min = minValue
        minCurrent = minValue

        binding?.blLeft1?.setOnClickListener {
            minCurrent -= 1
            binding?.sfMinValue?.text = minCurrent.toString()
            checkMinL()
            checkMinR()

        }
        binding?.blLeft2?.setOnClickListener {
            minCurrent += 1
            binding?.sfMinValue?.text = minCurrent.toString()
            checkMinR()
            checkMinL()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        filterComponentViewModel.filterComponent.inject(this)
    }

    override fun selected(tab: BesieTabSelected) {
        var index = when(tab){
            BesieTabSelected.ALL -> 0
            BesieTabSelected.MAN -> 1
            BesieTabSelected.GIRLS -> 2
            else -> 0
        }
        viewModel.setSex(index)
    }

}