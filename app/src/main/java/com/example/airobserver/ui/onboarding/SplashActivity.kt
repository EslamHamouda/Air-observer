package com.example.airobserver.ui.onboarding

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.airobserver.R
import com.example.airobserver.di.SharedPref
import com.example.airobserver.ui.auth.AuthActivity
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {
    @Inject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(applicationContext, OnBoardingActivity::class.java))
            finish()
        }, 500) // delay time in milliseconds (here 0.5 seconds)
    }
}