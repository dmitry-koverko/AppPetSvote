package com.petsvote.splash

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.petsvote.core.BaseFragment
import com.petsvote.navigation.MainNavigation
import com.petsvote.splash.databinding.FragmentSplashBinding
import com.petsvote.splash.di.SplashComponentViewModel
import javax.inject.Inject
import dagger.Lazy
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import me.vponomarenko.injectionmanager.x.XInjectionManager

class SplashFragment : BaseFragment(R.layout.fragment_splash) {

    private var binding: FragmentSplashBinding? = null

    private val navigation: MainNavigation by lazy {
        XInjectionManager.findComponent<MainNavigation>()
    }

    @Inject
    internal lateinit var splashViewModelFactory: Lazy<SplashViewModel.Factory>

    private val splashComponentViewModel: SplashComponentViewModel by viewModels()
    private val viewModel: SplashViewModel by viewModels {
        splashViewModelFactory.get()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSplashBinding.bind(view)
        lifecycleScope.launchWhenStarted { viewModel.checkLogin() }
    }


    override fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel._isLoginUser.collect { it ->
                if (it == null) return@collect
                if (it == false) {
                    try {navigation.startRegister()} catch (e: Exception) {}
                }else navigation.startTabs()
            }
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        splashComponentViewModel.splashComponent.inject(this)
    }
}