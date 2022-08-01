package com.petsvote.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.petsvote.navigation.MainNavigation
import com.petswote.pet.photos.PetPhotosActivity

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
        //navController?.navigate(R.id.action_splashFragment_to_filterFragment)
    }

    override fun startSimpleUserFromSplash() {
        navController?.navigate(R.id.action_splashFragment_to_simpleUserFragment)
    }

    override fun startProfileFromSplash() {
        navController?.navigate(R.id.action_splashFragment_to_userProfileFragment)
    }

    override fun startAddPetFromSplash() {
        navController?.navigate(R.id.action_splashFragment_to_addPetFragment)
    }

    override fun startFindPetFromSplash() {
        navController?.navigate(R.id.action_splashFragment_to_findPetFragment)
    }

    override fun startFilter() {

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

    override fun startSelectCity() {
        navController?.navigate(R.id.action_userProfileFragment_to_selectCityFragment)
    }

    override fun startSelectKindsFromAddPet() {
        navController?.navigate(R.id.action_addPetFragment_to_petSelectKindsFragment)
    }

    override fun startSelectBreedsFromAddPet() {
        navController?.navigate(R.id.action_addPetFragment_to_petSelectBreedsFragment)
    }

    override fun startSelectKindsFromEditPet(activity: Activity) {
        var intent = Intent(activity, SelectPetKindActivity::class.java)
        startActivityForResult(activity, intent, 123, bundleOf())
    }

    override fun startSelectBreedsFromEditPet(activity: Activity) {
        var intent = Intent(activity, SelectpetBreedctivity::class.java)
        startActivityForResult(activity, intent, 123, bundleOf())
    }

    override fun startFilterActivityForResult(activity: Activity) {
        activity.startActivity(Intent(activity, FilterActivity::class.java))
    }

    override fun startActivityFindPet(activity: Activity) {
        activity.startActivity(Intent(activity, FindPetActivity::class.java))
    }

    override fun startActivityPetInfo(activity: Activity, bundle: Bundle?) {
        var intent = Intent(activity, PetInfoActivity::class.java)
        bundle?.let { intent.putExtras(it) }
        startActivityForResult(activity, intent, 123, bundle)
    }

    override fun startActivityPetPhotos(activity: Activity, bundle: Bundle?) {
        var intent = Intent(activity, PetPhotosActivity::class.java)
        bundle?.let { intent.putExtras(it) }
        startActivityForResult(activity, intent, 123, bundle)
    }

    override fun startActivityEditPet(activity: Activity, bundle: Bundle?) {
        var intent = Intent(activity, EditPetActivity::class.java)
        bundle?.let { intent.putExtras(it) }
        startActivityForResult(activity, intent, 123, bundle)
    }

    override fun startEditFragment(bundle: Bundle?) {
        navController?.navigate(R.id.addPetFragment2, bundle)
    }

    override fun startActivityAddPet(activity: Activity, bundle: Bundle?) {
        var intent = Intent(activity, AddPetActivity::class.java)
        bundle?.let { intent.putExtras(it) }
        startActivityForResult(activity, intent, 123, bundle)
    }



    override fun back() {
        navController?.popBackStack()
    }

    override fun popUp() {
        navController?.navigateUp()
    }


}