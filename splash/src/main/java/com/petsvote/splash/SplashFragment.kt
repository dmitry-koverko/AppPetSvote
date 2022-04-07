package com.petsvote.splash

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.petsvote.core.BaseFragment
import com.petsvote.core.ext.log
import com.petsvote.splash.databinding.FragmentSplashBinding
import com.petsvote.splash.di.SplashComponentViewModel
import com.petsvote.ui.ext.loadFromResources
import com.petsvote.ui.ext.loadUrl
import javax.inject.Inject
import dagger.Lazy

class SplashFragment: BaseFragment(R.layout.fragment_splash) {

    private val ICON_TIME: Long = 2000
    private var binding: FragmentSplashBinding? = null

    @Inject
    internal lateinit var splashViewModelFactory: Lazy<SplashViewModel.Factory>

    private val splashComponentViewModel: SplashComponentViewModel by viewModels()
    private val viewModel: SplashViewModel by viewModels {
        splashViewModelFactory.get()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSplashBinding.bind(view)

        setUIStart()
        viewModel.get()
    }

    private fun setUIStart(){
        //binding?.icon?.loadFromResources(com.petsvote.ui.R.drawable.icon)

        object : CountDownTimer(ICON_TIME, ICON_TIME) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                binding?.icon?.isInvisible = true
                binding?.progressBar?.isVisible = true
            }
        }.start()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        splashComponentViewModel.splashComponent.inject(this)
    }
}