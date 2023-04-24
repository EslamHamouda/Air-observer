package com.example.airobserver.ui.home.news_fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.airobserver.databinding.FragmentPollutionsBinding
import com.example.airobserver.databinding.FragmentPolutionsDetailsBinding

class PollutionsDetailsActivity : AppCompatActivity() {
    lateinit var binding: FragmentPolutionsDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentPolutionsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.titlePollution.text = intent.getStringExtra("title")
        binding.describePollution.text = intent.getStringExtra("description")
    }
}