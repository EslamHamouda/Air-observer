package com.example.airobserver.ui.home.home_fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ekndev.gaugelibrary.Range
import com.example.airobserver.databinding.FragmentHomeBinding
import com.example.airobserver.utils.convertTo12HourFormat
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(inflater)
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        return binding.root
    }

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
        val range = Range()
        range.color = Color.parseColor("#FF0000")
        range.from = 0.0
        range.to = 50.0

        val range2 = Range()
        range2.color = Color.parseColor("#018ABE")
        range2.from = 50.0
        range2.to = 100.0



        //add color ranges to gauge
        binding.gauge.addRange(range)
        binding.gauge.addRange(range2)


        //set min max and current value
        binding.gauge.minValue = 0.0
        binding.gauge.maxValue = 150.0
        binding.gauge.value = 40.0

        /*val list = arrayListOf<gasmodel>()
        list.add(gasmodel("PMS2.5","Particulate Matter",22,"#FF0000","Good"))
        list.add(gasmodel("PMS10","Particulate Matter",25,"#8f3f97","Good"))
        list.add(gasmodel("CO","Carbon monoxide",22,"#7e0023","Good"))
        list.add(gasmodel("NO2","Nitrogen dioxide",33,"#00e400","Good"))
        list.add(gasmodel("SO2","PSulfur dioxide",15,"#ffff00","Good"))
        list.add(gasmodel("O3","Ozone",10,"#ff7e00","Good"))
        binding.rvDetailedReadings.adapter=DetailedReadingsAdapter(list)*/

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
        dataSet.setDrawFilled(true)

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

}