package com.example.airobserver.ui.home.history_fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.airobserver.databinding.FragmentHistoryBinding
import com.example.airobserver.ui.BaseFragment
import com.example.airobserver.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryFragment : BaseFragment() {
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

 /*   private fun setupLineChartData() {
        val points = ArrayList<Entry>()
        points.add(Entry(1f,  100f, "0"))
        points.add(Entry(2f,  50f, "1"))
        points.add(Entry(3f,  250f, "2"))
        points.add(Entry(4f,  500f, "3"))
        points.add(Entry(5f,  180f, "4"))
        points.add(Entry(6f, 140f, "5"))
        points.add(Entry(7f, 130f, "6"))
        points.add(Entry(8f,  100f, "0"))
        points.add(Entry(9f,  50f, "1"))
        points.add(Entry(10f,  250f, "2"))
        points.add(Entry(11f,  500f, "3"))
        points.add(Entry(12f,  180f, "4"))
        points.add(Entry(13f, 140f, "5"))
        points.add(Entry(14f, 130f, "6"))
        val dataSet = LineDataSet(points, "DataSet")


        // dataSet.fillAlpha = 110
        // dataSet.setFillColor(Color.RED)
        // set the line to be drawn like this "- - - - - -"
        // dataSet.enableDashedLine(5f, 5f, 0f)
        // dataSet.enableDashedHighlightLine(10f, 5f, 0f)

        dataSet.color = Color.parseColor("#018ABE")
        dataSet.setCircleColor(Color.parseColor("#00658D"))
        dataSet.lineWidth = 1f
        dataSet.circleRadius = 3f
        dataSet.setDrawCircleHole(false)
        dataSet.valueTextSize = 10f
        //dataSet.setDrawFilled(true)

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(dataSet)
        val data = LineData(dataSets)

        // set data
        binding.lineChart.data = data
        binding.lineChart.description.isEnabled = true
        binding.lineChart.description.text = "Today Air Quality Index"
        binding.lineChart.legend.isEnabled = false
        binding.lineChart.setPinchZoom(true)
        *//* binding.lineChart.xAxis.enableGridDashedLine(5f, 5f, 0f)
           binding.lineChart.axisLeft.enableGridDashedLine(5f, 5f, 0f)*//*
        binding.lineChart.xAxis.mAxisMinimum = 1f
        binding.lineChart.xAxis.mAxisMaximum = 31f
        binding.lineChart.axisLeft.axisMinimum = 0f
        binding.lineChart.axisLeft.axisMaximum = 500f
        binding.lineChart.axisRight.isEnabled = false

        //binding.lineChart.setDrawGridBackground(true)
        binding.lineChart.xAxis.labelCount = 10
        binding.lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
    }*/

}