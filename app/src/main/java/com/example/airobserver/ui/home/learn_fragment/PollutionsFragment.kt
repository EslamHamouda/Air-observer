package com.example.airobserver.ui.home.learn_fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.airobserver.R
import com.example.airobserver.databinding.FragmentPollutionsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PollutionsFragment : Fragment() {
    private lateinit var binding: FragmentPollutionsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPollutionsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.AQICard.setOnClickListener {
            findNavController().navigate(LearnFragmentDirections
                .actionLearnFragmentToPollutionsDetailsActivity(getString(R.string.title_AQI),getString(R.string.description_AQI)))
        }
        binding.COCard.setOnClickListener {
            findNavController().navigate(LearnFragmentDirections
                .actionLearnFragmentToPollutionsDetailsActivity(getString(R.string.title_CO),getString(R.string.description_CO)))
        }
        binding.PMCard.setOnClickListener {
            findNavController().navigate(LearnFragmentDirections
                .actionLearnFragmentToPollutionsDetailsActivity(getString(R.string.title_PM),getString(R.string.description_PM)))
        }
        binding.NO2Card.setOnClickListener {
            findNavController().navigate(LearnFragmentDirections
                .actionLearnFragmentToPollutionsDetailsActivity(getString(R.string.title_NO2),getString(R.string.description_NO2)))
        }
        binding.SO2Card.setOnClickListener {
            findNavController().navigate(LearnFragmentDirections
                .actionLearnFragmentToPollutionsDetailsActivity(getString(R.string.title_SO2),getString(R.string.description_SO2)))
        }
        binding.O3Card.setOnClickListener {
            findNavController().navigate(LearnFragmentDirections
                .actionLearnFragmentToPollutionsDetailsActivity(getString(R.string.title_O3),getString(R.string.description_O3)))

        }

    }
}