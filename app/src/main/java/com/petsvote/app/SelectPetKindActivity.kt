package com.petsvote.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.petswote.pet.find.FindPetFragment
import com.petswote.pet.kinds.PetSelectKindsFragment

class SelectPetKindActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_pet_kind)


        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<PetSelectKindsFragment>(R.id.container)
        }
    }
}