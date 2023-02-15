package com.example.airobserver.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.viewpager2.widget.ViewPager2
import com.example.airobserver.R
import com.example.airobserver.databinding.ActivityOnBoardingBinding
import com.example.airobserver.di.SharedPref
import com.example.airobserver.ui.onboarding.ViewPagerAdapter
import com.example.airobserver.utils.putData
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {

    lateinit var binding: ActivityOnBoardingBinding
    private var images = arrayListOf<Int>()
    private var headings = arrayListOf<Int>()
    private var description = arrayListOf<Int>()
    @Inject
    lateinit var pref: SharedPreferences
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isFirstRun()
        initViewPagerResources()

        binding.btnNext.setOnClickListener {
            if (getItem(0) < 2) binding.viewPager2.setCurrentItem(
                getItem(1),
                true
            )
        }

        binding.btnPrevious.setOnClickListener {
            if (getItem(0) > 0) {
                binding.viewPager2.setCurrentItem(getItem(-1), true)
            }

        }

        binding.btnSkip.setOnClickListener {
            pref.putData(SharedPref.FIRST_RUN,true)
            val i = Intent(applicationContext, AuthActivity::class.java)
            startActivity(i)
            finish()
        }

        binding.btnGetStarted.setOnClickListener {
            pref.putData(SharedPref.FIRST_RUN,true)
            val i = Intent(applicationContext, AuthActivity::class.java)
            startActivity(i)
            finish()
        }

        binding.viewPager2.adapter = ViewPagerAdapter(this, images, headings, description)
        binding.dotsIndicator.attachTo(binding.viewPager2)
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position > 0) {
                    binding.btnPrevious.visibility = View.VISIBLE
                } else {
                    binding.btnPrevious.visibility = View.INVISIBLE
                }
                if (position == 2) {
                    binding.btnGetStarted.visibility = View.VISIBLE
                    binding.btnNext.visibility = View.INVISIBLE
                    binding.btnSkip.visibility = View.INVISIBLE
                } else if (position < 2) {
                    binding.btnGetStarted.visibility = View.INVISIBLE
                    binding.btnNext.visibility = View.VISIBLE
                    binding.btnSkip.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun getItem(i: Int): Int {
        return binding.viewPager2.currentItem + i
    }

    private fun initViewPagerResources() {
        images = arrayListOf(
            R.drawable.undraw_environment,
            R.drawable.undraw_flying_drone,
            R.drawable.undraw_qa_engineers
        )
        headings = arrayListOf(
            R.string.head_title_AirQuality,
            R.string.head_title_SelectArea,
            R.string.head_title_Predicting
        )
        description = arrayListOf(
            R.string.description_AirQuality,
            R.string.description_SelectArea,
            R.string.description_Predicting
        )
    }

    private fun isFirstRun(){
        if (pref.getBoolean(SharedPref.FIRST_RUN,false)){
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }
    }
}