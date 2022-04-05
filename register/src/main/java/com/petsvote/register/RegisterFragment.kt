package com.petsvote.register

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.petsvote.core.BaseFragment
import com.petsvote.core.ext.log
import com.petsvote.register.databinding.FragmentRegisterBinding

class RegisterFragment: BaseFragment(R.layout.fragment_register) {

    private var binding: FragmentRegisterBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterBinding.bind(view)
        log("onViewCreated")
    }

}