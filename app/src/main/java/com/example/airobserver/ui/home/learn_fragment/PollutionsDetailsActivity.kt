package com.example.airobserver.ui.home.learn_fragment

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.airobserver.R
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
    }

}