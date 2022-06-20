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
        navController?.navigate(R.id.action_termsFragment_to_termsInfoFragment, bundle)
    }

    override fun backSplashFromRegister() {
        navController?.navigate(R.id.action_registerFragment_to_splashFragment)
    }

    override fun startTabs() {
        navController?.navigate(R.id.action_splashFragment_to_tabsFragment)
    }

    override fun startFilterFromSplash() {
        navController?.navigate(R.id.action_splashFragment_to_filterFragment)
    }

    override fun startSimpleUserFromSplash() {
        navController?.navigate(R.id.action_splashFragment_to_simpleUserFragment)
    }

    override fun startProfileFromSplash() {
        navController?.navigate(R.id.action_splashFragment_to_userProfileFragment)
    }

    override fun startFilter() {
        navController?.navigate(R.id.action_tabsFragment_to_filterFragment)
    }

    override fun startSelectKinds() {
        navController?.navigate(R.id.action_filterFragment_to_selectKindsFragment)
    }

    override fun startSelectBreeds() {
        navController?.navigate(R.id.action_filterFragment_to_selectBreedsFragment)
    }

    override fun startUserCrop() {
        navController?.navigate(R.id.action_userProfileFragment_to_cropUserImageFragment)
    }

    override fun startSelectCountry() {
        navController?.navigate(R.id.action_userProfileFragment_to_selectCountryFragment)
    }

    override fun back() {
        navController?.popBackStack()
    }

    override fun popUp() {
        navController?.navigateUp()
    }
}