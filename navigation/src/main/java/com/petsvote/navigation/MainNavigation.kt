package com.petsvote.navigation

import android.os.Bundle

interface MainNavigation {

    fun startRegister()
    fun startTerms()
    fun startInfoTerms(bundle: Bundle = Bundle())
    fun backSplashFromRegister()
    fun startTabs()
    fun startFilterFromSplash()
    fun startSimpleUserFromSplash()
    fun startProfileFromSplash()
    fun startFilter()
    fun startSelectKinds()
    fun startSelectBreeds()
    fun startUserCrop()

    fun back()
    fun popUp()

}