package com.example.airobserver.ui.home.home_fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.ekndev.gaugelibrary.Range
import com.example.airobserver.databinding.FragmentHomeBinding
import com.example.airobserver.domain.model.gasmodel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
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
        val yVals = ArrayList<Entry>()
        yVals.add(Entry(0f, 30f, "0"))
        yVals.add(Entry(1f, 2f, "1"))
        yVals.add(Entry(2f, 4f, "2"))
        yVals.add(Entry(3f, 6f, "3"))
        yVals.add(Entry(4f, 8f, "4"))
        yVals.add(Entry(5f, 10f, "5"))
        yVals.add(Entry(6f, 22f, "6"))
        yVals.add(Entry(7f, 12.5f, "7"))
        yVals.add(Entry(8f, 22f, "8"))
        yVals.add(Entry(9f, 32f, "9"))
        yVals.add(Entry(10f, 54f, "10"))
        yVals.add(Entry(11f, 28f, "11"))
        val set1 = LineDataSet(yVals, "DataSet 1")

        // set1.fillAlpha = 110
        // set1.setFillColor(Color.RED)
        // set the line to be drawn like this "- - - - - -"
        // set1.enableDashedLine(5f, 5f, 0f)
        // set1.enableDashedHighlightLine(10f, 5f, 0f)

        set1.color = Color.parseColor("#018ABE")
        set1.setCircleColor(Color.parseColor("#00658D"))
        set1.lineWidth = 1f
        set1.circleRadius = 3f
        set1.setDrawCircleHole(false)
        set1.valueTextSize = 0f
        set1.setDrawFilled(true)

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(set1)
        val data = LineData(dataSets)

        // set data
        binding.lineChart.data = data
        binding.lineChart.description.isEnabled = false
        binding.lineChart.legend.isEnabled = false
        binding.lineChart.setPinchZoom(true)
        binding.lineChart.xAxis.enableGridDashedLine(5f, 5f, 0f)
        binding.lineChart.axisRight.enableGridDashedLine(5f, 5f, 0f)
        binding.lineChart.axisLeft.enableGridDashedLine(5f, 5f, 0f)
        binding.lineChart.axisRight.isEnabled = false
        //binding.lineChart.setDrawGridBackground(true)
        binding.lineChart.xAxis.labelCount = 11
        binding.lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
    }


}