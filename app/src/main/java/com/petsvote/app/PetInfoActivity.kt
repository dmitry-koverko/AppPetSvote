package com.petsvote.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.petswote.pet.find.FindPetFragment
import com.petswote.pet.info.PetInfoFragment
import kotlinx.serialization.json.Json

class PetInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_info)

        var fragment = PetInfoFragment.newInstance(intent.extras?.getInt("pet"), intent.extras?.getBoolean("myPet") ?: false, 0)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.container, fragment)
        }
    }
}