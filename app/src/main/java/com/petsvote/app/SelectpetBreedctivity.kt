package com.petsvote.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.petswote.pet.breeds.PetSelectBreedsFragment
import com.petswote.pet.kinds.PetSelectKindsFragment

class SelectpetBreedctivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selectpet_breedctivity)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<PetSelectBreedsFragment>(R.id.container)
        }
    }
}