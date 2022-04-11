package com.petsvote.navigation

import android.os.Bundle

interface MainNavigation {

    fun startRegister()
    fun startTerms()
    fun startInfoTerms(bundle: Bundle = Bundle())

    fun back()
    fun popUp()

}