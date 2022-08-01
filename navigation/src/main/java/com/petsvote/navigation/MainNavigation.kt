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
    fun startSelectKindsFromEditPet(activity: Activity)
    fun startSelectBreedsFromEditPet(activity: Activity)
    fun startFilterActivityForResult(activity: Activity)
    fun startActivityFindPet(activity: Activity)
    fun startActivityPetInfo(activity: Activity, bundle: Bundle? = null)
    fun startActivityPetPhotos(activity: Activity, bundle: Bundle? = null)
    fun startActivityEditPet(activity: Activity, bundle: Bundle? = null)
    fun startEditFragment(bundle: Bundle?)
    fun startActivityAddPet(activity: Activity, bundle: Bundle? = null)


    fun back()
    fun popUp()

}