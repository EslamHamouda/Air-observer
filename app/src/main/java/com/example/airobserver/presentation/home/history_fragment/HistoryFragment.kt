package com.example.airobserver.presentation.home.history_fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.airobserver.databinding.FragmentHistoryBinding
import com.example.airobserver.presentation.viewmodel.HomeViewModel
import com.example.airobserver.utils.dataResponseHandling
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryFragment : Fragment() {
    lateinit var binding:FragmentHistoryBinding
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=FragmentHistoryBinding.inflate(inflater)
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        viewModel.aqiGraphHistory()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getAqiHistory()
        getAqiGraphHistory()

        binding.ivNext.setOnClickListener {
            if (binding.viewPager2.currentItem >= 0) binding.viewPager2.setCurrentItem(
                binding.viewPager2.currentItem + 1,
                true
            )
        }
        binding.ivBack.setOnClickListener {
            if (binding.viewPager2.currentItem > 0) {
                binding.viewPager2.setCurrentItem(binding.viewPager2.currentItem - 1, true)
            }
        }
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.aqiHistory(position+1)
                if (position == 0) {
                    binding.ivBack.visibility = View.INVISIBLE
                } else {
                    binding.ivBack.visibility = View.VISIBLE
                }
                if (binding.viewPager2.adapter?.itemCount?.minus(1)==position) {
                    binding.ivNext.visibility = View.INVISIBLE
                } else {
                    binding.ivNext.visibility = View.VISIBLE
                }
            }
        })
        binding.viewPager2.offscreenPageLimit = 12

        binding.swipeRefresh.setOnRefreshListener {
            // update data
            viewModel.aqiGraphHistory()
            //getAqiHistory()
            //getAqiGraphHistory()
            // stop refreshing when task is completed
            binding.swipeRefresh.isRefreshing = false
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getAqiHistory() {
        lifecycleScope.launch {
            viewModel.aqiHistoryResponse.flowWithLifecycle(
                lifecycle,
                Lifecycle.State.STARTED
            )
                .collectLatest {
                    dataResponseHandling(this@HistoryFragment.requireActivity(),
                        it,
                        binding.progressBar.progressBar,
                        {
                        },
                        { it1 ->
                            binding.rvDetailedReadings.adapter = DetailedReadingsAdapter(it1.daysDetails)
                        })
                }

        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getAqiGraphHistory() {
        lifecycleScope.launch {
            viewModel.aqiGraphHistoryResponse.flowWithLifecycle(
                lifecycle,
                Lifecycle.State.STARTED
            )
                .collectLatest {
                    dataResponseHandling(this@HistoryFragment.requireActivity(),
                        it,
                        binding.progressBar.progressBar,
                        {
                            viewModel.aqiGraphHistory()
                        },
                        { it1 ->
                            binding.viewPager2.adapter = HistoryViewPagerAdapter(it1)
                        })
                }

        }
    }

}