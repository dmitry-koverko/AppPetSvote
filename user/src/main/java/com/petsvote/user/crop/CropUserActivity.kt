package com.petsvote.user.crop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.petsvote.user.R

class CropUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crop_user)

//        var args = intent.getByteArrayExtra("CROP_MESSAGE")
//        var ds = args?.size ?: byteArrayOf()

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<CropUserImageFragment>(R.id.container)
        }

    }
}