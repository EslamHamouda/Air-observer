package com.example.airobserver.ui.home.learn_fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.airobserver.databinding.FragmentLearnBinding
import com.example.airobserver.utils.*
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LearnFragment : Fragment() {
    private lateinit var binding: FragmentLearnBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLearnBinding.inflate(inflater)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragments = listOf(NewsFragment(), PollutionsFragment())
        val adapter = ViewPagerAdapterFragments(requireActivity().supportFragmentManager, lifecycle, fragments)
        binding.viewPager2.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = "News"
                1 -> tab.text = "Pollutions"
            }
        }.attach()
}}