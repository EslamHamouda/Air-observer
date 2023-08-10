package com.example.airobserver.presentation.home.home_fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieAnimationView
import com.ekndev.gaugelibrary.Range
import com.example.airobserver.R
import com.example.airobserver.databinding.FragmentHomeBinding
import com.example.airobserver.domain.model.BaseResponse
import com.example.airobserver.presentation.viewmodel.HomeViewModel
import com.example.airobserver.utils.*
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(inflater)
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        viewModel.aqiOfDay()
        viewModel.aqiGraphLastHours()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getAqiOfDay()
        getAqiGraphLastHours()

        binding.swipeRefresh.setOnRefreshListener {
            // update data
            viewModel.aqiOfDay()
            viewModel.aqiGraphLastHours()
            // stop refreshing when task is completed
            binding.swipeRefresh.isRefreshing = false
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getAqiOfDay() {
        lifecycleScope.launch {
            viewModel.aqiOfDayResponse.flowWithLifecycle(
                lifecycle,
                Lifecycle.State.STARTED
            )
                .collectLatest {
                    dataResponseHandling(this@HomeFragment.requireActivity(),
                        it,
                        binding.progressBar.progressBar,
                        {
                            viewModel.aqiGraphHistory()
                        },
                        { it1 ->
                            it1.Max?.let { it2 -> setAQI(it2.toInt()) }
                            binding.tvAqiFeedback.text = it1.Category
                            binding.tvNote.text = it1.Note+"\n"
                            it1.PM10?.let { it2 -> setDetailedAqi(it2.toDouble(),binding.detailedAqi.tvPm10) } // setTextColor and it depends on the value that is retrieved from database
                            binding.detailedAqi.tvPm10.text = it1.PM10
                            it1.PM25?.let { it2 -> setDetailedAqi(it2.toDouble(),binding.detailedAqi.tvPm25) } // setTextColor and it depends on the value that is retrieved from database
                            binding.detailedAqi.tvPm25.text = it1.PM25
                            it1.CO?.let { it2 -> setDetailedAqi(it2.toDouble(),binding.detailedAqi.tvCo) } // setTextColor and it depends on the value that is retrieved from database
                            binding.detailedAqi.tvCo.text = it1.CO
                            it1.NO2?.let { it2 -> setDetailedAqi(it2.toDouble(),binding.detailedAqi.tvNo2) } // setTextColor and it depends on the value that is retrieved from database
                            binding.detailedAqi.tvNo2.text = it1.NO2
                            it1.SO2?.let { it2 -> setDetailedAqi(it2.toDouble(),binding.detailedAqi.tvSo2) } // setTextColor and it depends on the value that is retrieved from database
                            binding.detailedAqi.tvSo2.text = it1.SO2
                            it1.O3?.let { it2 -> setDetailedAqi(it2.toDouble(),binding.detailedAqi.tvO3) } // setTextColor and it depends on the value that is retrieved from database
                            binding.detailedAqi.tvO3.text = it1.O3
                        })
                }

        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getAqiGraphLastHours() {
        lifecycleScope.launch {
            viewModel.aqiGraphLastHoursResponse.flowWithLifecycle(
                lifecycle,
                Lifecycle.State.STARTED
            )
                .collectLatest {
                    dataResponseHandling(this@HomeFragment.requireActivity(),
                        it,
                        binding.progressBar.progressBar,
                        {
                            viewModel.aqiGraphLastHours()
                        },
                        { it1 ->
                            val points = ArrayList<Entry>()
                            for (i in it1){
                                points.add(Entry(i.Time!!.subSequence(0..1).toString().toFloat(),  i.AQI!!.toFloat()))
                            }

                            val dataSet = LineDataSet(points, "DataSet")

                            dataSet.color = Color.parseColor("#018ABE")
                            dataSet.setCircleColor(Color.parseColor("#00658D"))
                            dataSet.lineWidth = 1f
                            dataSet.circleRadius = 3f
                            dataSet.setDrawCircleHole(false)
                            //dataSet.valueTextSize = 10f
                            //dataSet.setDrawFilled(true)

                            val dataSets = ArrayList<ILineDataSet>()
                            dataSets.add(dataSet)
                            val data = LineData(dataSets)

                            // set data
                            binding.lineChart.data = data
                            binding.lineChart.description.isEnabled = true
                            binding.lineChart.description.text = "Daily"
                            binding.lineChart.legend.isEnabled = false
                            binding.lineChart.setPinchZoom(true)
                            /* binding.lineChart.xAxis.enableGridDashedLine(5f, 5f, 0f)
                               binding.lineChart.axisLeft.enableGridDashedLine(5f, 5f, 0f)*/
                            binding.lineChart.xAxis.mAxisMinimum = 0f
                            binding.lineChart.xAxis.mAxisMaximum = 24f
                            binding.lineChart.axisLeft.axisMinimum = 0f
                            binding.lineChart.axisLeft.axisMaximum = 500f
                            binding.lineChart.axisRight.isEnabled = false
                            binding.lineChart.xAxis.valueFormatter = object : ValueFormatter() {
                                override fun getFormattedValue(value: Float): String {
                                    return convertTo12HourFormat(value.toInt())
                                }
                            }
                            //binding.lineChart.setDrawGridBackground(true)

                            binding.lineChart.xAxis.setCenterAxisLabels(true)
                            binding.lineChart.xAxis.labelCount = 5
                            binding.lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM

                        })
                }

        }
    }

    private fun setDetailedAqi(value: Double, txt:TextView) {
        when (value) {
            in 0.0 .. 50.0 -> {
                txt.setTextColor(Color.parseColor("#488A5A"))
                txt.setBackgroundResource(R.drawable.circle_background_green)
            }
            in 51.0 .. 100.0 -> {
                txt.setTextColor(Color.parseColor("#ddad25"))
                txt.setBackgroundResource(R.drawable.circle_background_yellow)
            }
            in 101.0 .. 150.0 -> {
                txt.setTextColor(Color.parseColor("#fc5b00"))
                txt.setBackgroundResource(R.drawable.circle_background_orange)
            }
            in 151.0 .. 200.0 -> {
                txt.setTextColor(Color.parseColor("#c72c2c"))
                txt.setBackgroundResource(R.drawable.circle_background_red)
            }
            in 201.0 .. 300.0 -> {
                txt.setTextColor(Color.parseColor("#6A359C"))
                txt.setBackgroundResource(R.drawable.circle_background_purple)
            }
            in 301.0 .. 500.0 -> {
                txt.setTextColor(Color.parseColor("#800000"))
                txt.setBackgroundResource(R.drawable.circle_background_maroon)
            }
        }
    }

    private fun setAQI(value: Int) {
        val range1 = Range()
        range1.color = Color.parseColor("#4ba162")
        range1.from = 0.0
        range1.to = 50.0
        val range2 = Range()
        range2.color = Color.parseColor("#d9a52d")
        range2.from = 51.0
        range2.to = 100.0
        val range3 = Range()
        range3.color = Color.parseColor("#FF4500")
        range3.from = 101.0
        range3.to = 150.0
        val range4 = Range()
        range4.color = Color.parseColor("#ba3030")
        range4.from = 151.0
        range4.to = 200.0
        val range5 = Range()
        range5.color = Color.parseColor("#552586")
        range5.from = 201.0
        range5.to = 300.0
        val range6 = Range()
        range6.color = Color.parseColor("#7E0023")
        range6.from = 301.0
        range6.to = 500.0

        when (value) {
            in 0 .. 50 -> {
                binding.cardAQI.setBackgroundResource(R.drawable.gradient_green)
            }
            in 51 .. 100 -> {
                binding.cardAQI.setBackgroundResource(R.drawable.gradient_yellow)
            }
            in 101 .. 150 -> {
                binding.cardAQI.setBackgroundResource(R.drawable.gradient_orange)
                binding.cloudImg.alpha = 0.6f
            }
            in 151 .. 200 -> {
                binding.cardAQI.setBackgroundResource(R.drawable.gradient_red)
                binding.cloudImg.alpha = 0.4f
            }
            in 201 .. 300 -> {
                binding.cardAQI.setBackgroundResource(R.drawable.gradient_purple)
                binding.cloudImg.alpha = 0.3f
            }
            in 301 .. 500 -> {
                binding.cardAQI.setBackgroundResource(R.drawable.gradient_maroon)
                binding.cloudImg.alpha = 0.4f
            }
        }

        //add color ranges to gauge
        binding.gauge.addRange(range1)
        binding.gauge.addRange(range2)
        binding.gauge.addRange(range3)
        binding.gauge.addRange(range4)
        binding.gauge.addRange(range5)
        binding.gauge.addRange(range6)

        //set min max and current value
        binding.gauge.minValue = 0.0
        binding.gauge.maxValue = 500.0
        binding.gauge.value = value.toDouble()
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun <B : BaseResponse<T>, T> dataResponseHandling(
        activity: Activity,
        it: ApiResponseStates<B>,
        progressBar: LottieAnimationView,
        tryAgain: () -> Unit,
        successFunc: (T) -> Unit,
        errorCode: Int? = 0, handleError: (() -> Unit)? = null,
    ) {
        when (it) {
            is ApiResponseStates.Success -> {
                progressBar?.let { it1 -> hideProgress(it1) }
                it.value?.let {
                    (it as BaseResponse<T>).handle(activity, successFunc)
                }
            }
            is ApiResponseStates.Loading -> {
                progressBar?.let { it1 -> showProgress(it1) }
            }
            is ApiResponseStates.NetworkError -> {
                progressBar?.let { it1 -> hideProgress(it1) }
                showSnackbar(it.throwable.handling(activity), activity) { tryAgain() }

            }
        }
    }
}