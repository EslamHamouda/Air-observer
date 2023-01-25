package com.example.airobserver.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.viewpager2.widget.ViewPager2
import com.example.airobserver.R
import com.example.airobserver.databinding.ActivityOnBoardingBinding
import com.example.airobserver.ui.home.ProfileFragment
import com.example.airobserver.ui.onboarding.SaveState
import com.example.airobserver.ui.onboarding.ViewPagerAdapter

class OnBoardingActivity : AppCompatActivity() {

    lateinit var binding: ActivityOnBoardingBinding
    private var saveState: SaveState? = null
    private var images = arrayListOf<Int>()
    private var headings = arrayListOf<Int>()
    private var description = arrayListOf<Int>()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewPagerResources()
        isFirstRun()

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
            saveState!!.state = 1
            val i = Intent(applicationContext, AuthActivity::class.java)
            startActivity(i)
            finish()
        }

        binding.btnGetStarted.setOnClickListener {
            saveState!!.state = 1
            val i = Intent(applicationContext, AuthActivity::class.java)
            startActivity(i)
            finish()
        }

        binding.viewPager2.adapter = ViewPagerAdapter(this, images, headings, description)
        binding.dotsIndicator.attachTo(binding.viewPager2)
        binding.viewPager2.registerOnPageChangeCallback(viewListener)
    }

    private var viewListener: ViewPager2.OnPageChangeCallback =
        object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }
            @RequiresApi(api = Build.VERSION_CODES.M)
            override fun onPageSelected(position: Int) {
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
            override fun onPageScrollStateChanged(state: Int) {}
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
        saveState = SaveState(this, "onBoarding")
        if (saveState!!.state == 1) {
            val i = Intent(this, AuthActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}