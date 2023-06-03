package com.example.airobserver.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.airobserver.R
import com.example.airobserver.databinding.ActivityOnBoardingBinding
import com.example.airobserver.databinding.ActivitySplashBinding
import javax.inject.Inject


class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val anim = AnimationUtils.loadAnimation(applicationContext, com.example.airobserver.R.anim.splash_anim)
        binding.gifImageView.startAnimation(anim)

        // the purpose of this Handler is to introduce a delay of 3 seconds before starting the `OnBoardingActivity`
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(applicationContext, OnBoardingActivity::class.java))
            finish()
        }, 3000) // delay time in milliseconds (here 3 seconds)
    }
}