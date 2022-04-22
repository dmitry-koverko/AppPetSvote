package com.petsvote.register

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.petsvote.core.BaseFragment
import com.petsvote.core.ext.log
import com.petsvote.core.ext.stateLoading
import com.petsvote.navigation.MainNavigation
import com.petsvote.register.databinding.FragmentRegisterBinding
import com.petsvote.register.di.RegisterComponentViewModel
import dagger.Lazy
import kotlinx.coroutines.flow.collect
import me.vponomarenko.injectionmanager.x.XInjectionManager
import javax.inject.Inject
import kotlin.random.Random

class RegisterFragment: BaseFragment(R.layout.fragment_register) {

    private val navigation: MainNavigation by lazy {
        XInjectionManager.findComponent<MainNavigation>()
    }

    private var binding: FragmentRegisterBinding? = null

    lateinit var mGoogleSignInClient: GoogleSignInClient
    val RC_SIGN_IN:Int= 123

    @Inject
    internal lateinit var splashViewModelFactory: Lazy<RegisterViewModel.Factory>

    private val splashComponentViewModel: RegisterComponentViewModel by viewModels()
    private val viewModel: RegisterViewModel by viewModels {
        splashViewModelFactory.get()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterBinding.bind(view)
        initViews()
    }

    private fun initViews() {
        binding?.containerBottom?.setOnClickListener {
            signInGoogle()
        }

        binding?.legalBl?.setOnClickListener {
           navigation.startTerms()
        }
    }

    private  fun signInGoogle(){
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)

    }

    override fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel._isLoading.collect {
                stateView(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel._isRegister.collect {
                if(it) try {navigation.backSplashFromRegister()} catch (e: Exception){}
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }

    }
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? =completedTask.getResult(ApiException::class.java)
            if (account != null) {
                saveAccount(account)
            }
        } catch (e:ApiException){
            log(e.toString())
        }
    }

    private fun saveAccount(account: GoogleSignInAccount) {
        account.id?.let {
            viewModel.registerUser(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            //.requestIdToken(getString(R.string.default_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient= activity?.let { GoogleSignIn.getClient(it, gso) }!!;
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(
            true
        ) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
        splashComponentViewModel.registerComponent.inject(this)
    }

    private fun stateView(isLoading: Boolean){

        var contentVisible = if(isLoading) View.GONE else View.VISIBLE
        var progressVisible = if(isLoading) View.VISIBLE else View.GONE

        binding?.progress?.visibility = progressVisible
        binding?.containerMiddle?.visibility = contentVisible
        binding?.containerBottom?.visibility = contentVisible
        binding?.legalContainer?.visibility = contentVisible
    }

}