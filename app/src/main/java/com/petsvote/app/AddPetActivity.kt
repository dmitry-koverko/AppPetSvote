package com.petsvote.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import me.vponomarenko.injectionmanager.x.XInjectionManager

class AddPetActivity : AppCompatActivity() {

    private val navigator: Navigator by lazy {
        XInjectionManager.findComponent<Navigator>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pet)
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