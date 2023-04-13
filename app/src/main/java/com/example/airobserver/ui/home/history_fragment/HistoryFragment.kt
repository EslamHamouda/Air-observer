package com.example.airobserver.ui.home.history_fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.airobserver.R
import com.example.airobserver.databinding.FragmentHistoryBinding
import com.example.airobserver.utils.convertTo12HourFormat
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {
    lateinit var binding:FragmentHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=FragmentHistoryBinding.inflate(inflater)
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLineChartData()
        binding.rvDetailedReadings.adapter = DetailedReadingsAdapter(arrayListOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"))
    }

    private fun setupLineChartData() {
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
        binding.lineChart.xAxis.mAxisMinimum = 1f
        binding.lineChart.xAxis.mAxisMaximum = 31f
        binding.lineChart.axisLeft.axisMinimum = 0f
        binding.lineChart.axisLeft.axisMaximum = 500f
        binding.lineChart.axisRight.isEnabled = false

        //binding.lineChart.setDrawGridBackground(true)
        binding.lineChart.xAxis.labelCount = 10
        binding.lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
    }

}