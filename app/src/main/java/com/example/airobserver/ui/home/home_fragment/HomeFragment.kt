package com.example.airobserver.ui.home.home_fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.ekndev.gaugelibrary.Range
import com.example.airobserver.databinding.FragmentHomeBinding
import com.example.airobserver.domain.model.BaseResponse
import com.example.airobserver.ui.home.history_fragment.HistoryViewPagerAdapter
import com.example.airobserver.ui.viewmodel.HomeViewModel
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
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLineChartData()
        /*binding.aqi.settings.javaScriptEnabled = true;
        binding.aqi.loadUrl("file:///android_asset/index.html")
        binding.aqi.setOnTouchListener { v, event ->
            event.action == MotionEvent.ACTION_MOVE
        }
        binding.aqi.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                view.evaluateJavascript("setDial(500)", null);
            }
        }*/

        getAqiOfDay()


        /*val list = arrayListOf<gasmodel>()
        list.add(gasmodel("PMS2.5","Particulate Matter",22,"#FF0000","Good"))
        list.add(gasmodel("PMS10","Particulate Matter",25,"#8f3f97","Good"))
        list.add(gasmodel("CO","Carbon monoxide",22,"#7e0023","Good"))
        list.add(gasmodel("NO2","Nitrogen dioxide",33,"#00e400","Good"))
        list.add(gasmodel("SO2","PSulfur dioxide",15,"#ffff00","Good"))
        list.add(gasmodel("O3","Ozone",10,"#ff7e00","Good"))
        binding.rvDetailedReadings.adapter=DetailedReadingsAdapter(list)*/

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
                            setAQI(it1.MAX!!.toInt())
                            binding.tvAqiFeedback.text = it1.Category
                            binding.detailedAqi.tvPm10.text = it1.PM10
                            binding.detailedAqi.tvPm25.text = it1.PM25
                            binding.detailedAqi.tvCo.text = it1.CO
                            binding.detailedAqi.tvNo2.text = it1.NO2
                            binding.detailedAqi.tvSo2.text = it1.SO2
                            binding.detailedAqi.tvO3.text = it1.O3
                        })
                }

        }
    }

    private fun setAQI(value: Int) {
        val range1 = Range()
        range1.color = Color.parseColor("#00FF00")
        range1.from = 0.0
        range1.to = 50.0
        val range2 = Range()
        range2.color = Color.parseColor("#00FF00")
        range2.from = 51.0
        range2.to = 100.0
        val range3 = Range()
        range3.color = Color.parseColor("#FF7E00")
        range3.from = 101.0
        range3.to = 150.0
        val range4 = Range()
        range4.color = Color.parseColor("#FF0000")
        range4.from = 151.0
        range4.to = 200.0
        val range5 = Range()
        range5.color = Color.parseColor("#8F3F97")
        range5.from = 201.0
        range5.to = 300.0
        val range6 = Range()
        range6.color = Color.parseColor("#7E0023")
        range6.from = 301.0
        range6.to = 500.0

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

    private fun setupLineChartData() {
        val points = ArrayList<Entry>()
        points.add(Entry(0f,  100f, "0"))
        points.add(Entry(4f,  50f, "1"))
        points.add(Entry(8f,  250f, "2"))
        points.add(Entry(12f,  500f, "3"))
        points.add(Entry(16f,  180f, "4"))
        points.add(Entry(20f, 140f, "5"))
        points.add(Entry(24f, 130f, "6"))
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
        binding.lineChart.xAxis.labelCount = 6
        binding.lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun <B : BaseResponse<T>, T> dataResponseHandling(
        activity: Activity,
        it: ApiResponseStates<B>,
        progressBar: ProgressBar?,
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