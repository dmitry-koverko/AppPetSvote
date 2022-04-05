package com.petsvote.splash

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.petsvote.core.BaseFragment
import com.petsvote.core.ext.log
import com.petsvote.splash.databinding.FragmentSplashBinding
import com.petsvote.ui.ext.loadFromResources
import com.petsvote.ui.ext.loadUrl

class SplashFragment: BaseFragment(R.layout.fragment_splash), View.OnClickListener {

    private var binding: FragmentSplashBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSplashBinding.bind(view)

        setUIStart()
    }

    private fun setUIStart(){
        binding?.icon?.loadFromResources(com.petsvote.ui.R.drawable.icon)
        binding?.ripple?.mOnClickListener = this

        object : CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                binding?.icon?.isInvisible = true
                binding?.progressBar?.isVisible = true
            }
        }.start()
    }

    override fun onClick(p0: View?) {
        log("click besie")
    }


}