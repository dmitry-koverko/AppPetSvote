package com.petsvote.navigation

import android.os.Bundle

interface MainNavigation {

    fun startRegister()
    fun startTerms()
    fun startInfoTerms(bundle: Bundle = Bundle())
    fun backSplashFromRegister()
    fun startTabs()
    fun startFilter()
    fun startSelectKinds()
    fun startSelectBreeds()

    fun back()
    fun popUp()

}