package com.petsvote.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer

class SplashActivity : AppCompatActivity() {

    private val ICON_TIME: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setUIStart()
    }

    private fun setUIStart() {
        object : CountDownTimer(ICON_TIME, ICON_TIME) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                startActivity(Intent(baseContext, MainActivity::class.java))
                finish()
            }
        }.start()
    }
}