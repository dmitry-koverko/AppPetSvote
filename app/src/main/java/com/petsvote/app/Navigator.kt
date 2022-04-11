package com.petsvote.app

import android.os.Bundle
import androidx.navigation.NavController
import com.petsvote.navigation.MainNavigation

class Navigator: MainNavigation {

    private var navController: NavController? = null

    fun bind(navController: NavController) {
        this.navController = navController
    }

    fun unbind() {
        navController = null
    }

    override fun startRegister() {
        navController?.navigate(R.id.action_splashFragment_to_registerFragment)
    }

    override fun startTerms() {
        navController?.navigate(R.id.action_registerFragment_to_termsFragment)
    }

    override fun startInfoTerms(bundle: Bundle) {
        navController?.navigate(R.id.action_termsFragment_to_termsInfoFragment)
    }

    override fun back() {
        navController?.popBackStack()
    }

    override fun popUp() {
        navController?.navigateUp()
    }
}