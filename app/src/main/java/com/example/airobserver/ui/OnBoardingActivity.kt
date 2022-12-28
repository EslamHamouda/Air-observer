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
    var dots = ArrayList<TextView>(3)
    var saveState: SaveState? = null
    var images= arrayListOf<Int>()
    var headings= arrayListOf<Int>()
    var description= arrayListOf<Int>()
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewPagerResources()
        saveState = SaveState(this, "onBoarding")
        if (saveState!!.state == 1) {
            val i = Intent(this, ProfileFragment::class.java)
            startActivity(i)
            finish()
        }
        binding.nextButtonId.setOnClickListener(View.OnClickListener {
            if (getitem(0) < 2) binding.slideViewPagerId.setCurrentItem(
                getitem(1),
                true
            )
        })
        binding.previousButtonId.visibility = View.INVISIBLE
        binding.previousButtonId.setOnClickListener(View.OnClickListener {
            if (getitem(0) > 0) {
                binding.slideViewPagerId.setCurrentItem(getitem(-1), true)
            }
        })
        binding.skipButtonId.setOnClickListener(View.OnClickListener {
            saveState!!.state = 1
            val i = Intent(applicationContext, ProfileFragment::class.java)
            startActivity(i)
            finish()
        })
        binding.getStartButtonId.visibility = View.INVISIBLE
        binding.getStartButtonId.setOnClickListener(View.OnClickListener {
            saveState!!.state = 1
            val i = Intent(applicationContext, ProfileFragment::class.java)
            startActivity(i)
            finish()
        })
        binding.slideViewPagerId.adapter = ViewPagerAdapter(this,images, headings, description)
        setUpIndicator(0)
        binding.slideViewPagerId.registerOnPageChangeCallback(viewListener)
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    fun setUpIndicator(position: Int) {
        repeat(3) { dots.add(TextView(this)) }
        binding.indicatorLayout.removeAllViews()
        for (i in dots.indices) {
            dots[i].text = Html.fromHtml("&#8226;")
            dots[i].textSize = 35f
            dots[i]
                .setTextColor(resources.getColor(R.color.inactive, applicationContext.theme))
            binding.indicatorLayout.addView(dots[i])
        }
        dots[position]
            .setTextColor(resources.getColor(R.color.active, applicationContext.theme))
    }

    var viewListener: ViewPager2.OnPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        override fun onPageSelected(position: Int) {
            setUpIndicator(position)
            if (position > 0) {
                binding.previousButtonId.visibility = View.VISIBLE
            } else {
                binding.previousButtonId.visibility = View.INVISIBLE
            }
            if (position == 2) {
                binding.getStartButtonId.visibility = View.VISIBLE
                binding.nextButtonId.visibility = View.INVISIBLE
                binding.skipButtonId.visibility = View.INVISIBLE
            } else if (position < 2) {
                binding.getStartButtonId.visibility = View.INVISIBLE
                binding.nextButtonId.visibility = View.VISIBLE
                binding.skipButtonId.visibility = View.VISIBLE
            }
        }

        override fun onPageScrollStateChanged(state: Int) {}
    }

    private fun getitem(i: Int): Int {
        return binding.slideViewPagerId.currentItem + i
    }

    private fun initViewPagerResources(){
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
}