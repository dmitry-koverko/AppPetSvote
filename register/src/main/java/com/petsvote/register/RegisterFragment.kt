package com.petsvote.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.petsvote.core.BaseFragment
import com.petsvote.register.databinding.FragmentRegisterBinding

class RegisterFragment: BaseFragment(R.layout.fragment_register) {

    private var binding: FragmentRegisterBinding? = null

    lateinit var mGoogleSignInClient: GoogleSignInClient
    val RC_SIGN_IN:Int= 123

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterBinding.bind(view)
//        binding?.signInWithGoogle?.mOnClickListener = (View.OnClickListener {
//            signInGoogle()
//        })
    }

    private  fun signInGoogle(){
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)

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
            //firebaseAuthWithGoogle()
            //registerViewModel.getCurrensies("12345")
            Toast.makeText(context, e.message,Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveAccount(account: GoogleSignInAccount) {
//        account.id?.let {
//            binding.legalContainer.visibility = View.GONE
//            binding.containerMiddle.visibility = View.GONE
//            binding.containerBottom.visibility = View.GONE
//            binding.progress.visibility = View.VISIBLE
//            registerViewModel.getCurrensies(it)
//        }
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
    }

}