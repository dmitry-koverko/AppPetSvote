package com.petsvote.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.petswote.pet.find.FindPetFragment
import com.petswote.pet.info.PetInfoFragment

class PetInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_info)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<PetInfoFragment>(R.id.container)
        }
    }
}