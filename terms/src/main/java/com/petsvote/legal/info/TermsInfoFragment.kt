package com.petsvote.legal.info

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.petsvote.core.BaseFragment
import com.petsvote.legal.R
import com.petsvote.legal.databinding.FragmentTermsInfoBinding
import com.petsvote.legal.di.TermsComponentViewModel
import com.petsvote.navigation.MainNavigation
import dagger.Lazy
import kotlinx.coroutines.flow.collect
import me.vponomarenko.injectionmanager.x.XInjectionManager
import javax.inject.Inject

class TermsInfoFragment: BaseFragment(R.layout.fragment_terms_info) {

    private var binding: FragmentTermsInfoBinding? = null

    private val navigation: MainNavigation by lazy {
        XInjectionManager.findComponent<MainNavigation>()
    }

    @Inject
    internal lateinit var termsInfoViewModelFactory: Lazy<TermsInfoViewModel.Factory>

    private val termsComponentViewModel: TermsComponentViewModel by viewModels()
    private val viewModel: TermsInfoViewModel by viewModels {
        termsInfoViewModelFactory.get()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentTermsInfoBinding.bind(view)
        initViews()
        super.onViewCreated(view, savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initViews() {
//
//        var motionLayout = view?.findViewById<MotionLayout>(R.id.motion)
//
//        binding?.scroll?.setOnScrollChangeListener(@RequiresApi(Build.VERSION_CODES.M)
//        object: View.OnScrollChangeListener{
//            override fun onScrollChange(p0: View?, p1: Int, p2: Int, p3: Int, p4: Int) {
//                if(p2 in -50 .. 15) motionLayout?.transitionToStart()
//                else if(p2 > p4){ motionLayout?.transitionToEnd()}
//            }
//        })

        binding?.home?.setOnClickListener {
            navigation.back()
        }
    }

    override fun initObservers() {
        lifecycleScope.launchWhenStarted { this@TermsInfoFragment.arguments?.let {
            viewModel.loadData(
                it
            )
        } }

        lifecycleScope.launchWhenStarted {
            viewModel._dataTitle.collect {
                binding?.expandedImage?.text = it
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel._dataDescription.collect {
                binding?.text?.text = it
            }
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        termsComponentViewModel.termsComponent.injectTermsInfo(this)
    }

}