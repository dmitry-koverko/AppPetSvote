package com.petsvote.navigation

import android.app.Activity
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
    fun startAddPetFromSplash()
    fun startFindPetFromSplash()
    fun startFilter()
    fun startSelectKinds()
    fun startSelectBreeds()
    fun startUserCrop()
    fun startSelectCountry()
    fun startSelectCity()
    fun startSelectKindsFromAddPet()
    fun startSelectBreedsFromAddPet()
    fun startFilterActivityForResult(activity: Activity)
    fun startActivityFindPet(activity: Activity)
    fun startActivityPetInfo(activity: Activity, bundle: Bundle? = null)


    fun back()
    fun popUp()

}