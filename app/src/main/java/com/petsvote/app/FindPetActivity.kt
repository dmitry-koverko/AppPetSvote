package com.petsvote.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.findNavController
import com.petswote.pet.crop.CropPetImageFragment
import com.petswote.pet.find.FindPetFragment
import me.vponomarenko.injectionmanager.x.XInjectionManager

class FindPetActivity : AppCompatActivity() {

    private val navigator: Navigator by lazy {
        XInjectionManager.findComponent<Navigator>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_pet)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<FindPetFragment>(R.id.container)
        }
    }

}