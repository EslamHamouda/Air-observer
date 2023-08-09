package com.example.airobserver.presentation.home.learn_fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.example.airobserver.databinding.ActivityPollutionsDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PollutionsDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPollutionsDetailsBinding
    private val args:PollutionsDetailsActivityArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPollutionsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.materialToolbar.setNavigationOnClickListener {
            this.finish()
        }

        binding.titlePollution.text = args.title
        binding.describePollution.text = args.description

        binding.urlPollution.setOnClickListener {
            val url = "https://www.epa.gov"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }


    }

}