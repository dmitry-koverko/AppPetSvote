package com.petswote.pet.photos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.petswote.pet.R
import com.petswote.pet.info.PetInfoFragment

class PetPhotosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_photos)

        var fragment = PetPhotosFragment.newInstance(
            intent.extras?.getString("pet", ""),
            intent.extras?.getInt("position", 0) ?: 0,
            intent.extras?.getString("location", "") ?: "")
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.container, fragment)
        }
    }
}