package com.example.airobserver.ui.home.news_fragment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.airobserver.R
import com.example.airobserver.databinding.FragmentPollutionsBinding

class PollutionsActivity : AppCompatActivity() {
    lateinit var binding: FragmentPollutionsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentPollutionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.AQICard.setOnClickListener {
            val intent = Intent(this, PollutionsDetailsActivity::class.java)
            intent.putExtra("title", getString(R.string.title_AQI))
            intent.putExtra("description", getString(R.string.description_AQI))
            startActivity(intent)
        }

        binding.COCard.setOnClickListener {
            val intent = Intent(this, PollutionsDetailsActivity::class.java)
            intent.putExtra("title", getString(R.string.title_CO))
            intent.putExtra("description", getString(R.string.description_CO))
            startActivity(intent)
        }

        binding.PMCard.setOnClickListener {
            val intent = Intent(this, PollutionsDetailsActivity::class.java)
            intent.putExtra("title", getString(R.string.title_PM))
            intent.putExtra("description", getString(R.string.description_PM))
            startActivity(intent)
        }

        binding.NO2Card.setOnClickListener {
            val intent = Intent(this, PollutionsDetailsActivity::class.java)
            intent.putExtra("title", getString(R.string.title_NO2))
            intent.putExtra("description", getString(R.string.description_NO2))
            startActivity(intent)
        }

        binding.SO2Card.setOnClickListener {
            val intent = Intent(this, PollutionsDetailsActivity::class.java)
            intent.putExtra("title", getString(R.string.title_SO2))
            intent.putExtra("description", getString(R.string.description_SO2))
            startActivity(intent)
        }

        binding.O3Card.setOnClickListener {
            val intent = Intent(this, PollutionsDetailsActivity::class.java)
            intent.putExtra("title", getString(R.string.title_O3))
            intent.putExtra("description", getString(R.string.description_O3))
            startActivity(intent)
        }
    }
}