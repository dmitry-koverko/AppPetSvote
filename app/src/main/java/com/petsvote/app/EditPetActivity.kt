package com.petsvote.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import com.petswote.pet.edit.EditPetFragment
import me.vponomarenko.injectionmanager.x.XInjectionManager

class EditPetActivity : AppCompatActivity() {

    private val navigator: Navigator by lazy {
        XInjectionManager.findComponent<Navigator>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pet)
        navigator.startEditFragment(intent.extras)
//
//        var fragment = EditPetFragment.newInstance(intent.extras?.getString("pet"))
//        supportFragmentManager.commit {
//            setReorderingAllowed(true)
//            add(R.id.container, fragment)
//        }


    }

    override fun onResume() {
        super.onResume()
        navigator.bind(findNavController(R.id.nav_host_fragment))
    }

    override fun onPause() {
        super.onPause()
        navigator.unbind()
    }

}