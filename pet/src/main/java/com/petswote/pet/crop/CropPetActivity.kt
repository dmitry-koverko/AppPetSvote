package com.petswote.pet.crop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.petswote.pet.R

class CropPetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crop_pet)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<CropPetImageFragment>(R.id.container)
        }

    }
}