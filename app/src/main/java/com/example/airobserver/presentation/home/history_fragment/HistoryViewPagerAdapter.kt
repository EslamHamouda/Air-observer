package com.example.airobserver.presentation.home.history_fragment

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.airobserver.databinding.ItemGraphHistoryBinding
import com.example.airobserver.domain.model.response.AqiGraphHistoryResponse
import com.example.airobserver.domain.model.response.DaysDetails
import com.example.airobserver.utils.getMonthName
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

class HistoryViewPagerAdapter(
    private val list:List<AqiGraphHistoryResponse>
) : RecyclerView.Adapter<HistoryViewPagerAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemGraphHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item:AqiGraphHistoryResponse) {
           binding.tvTitle.text= getMonthName(item.month!!.toInt())
            //listener.onItemChanged(item.daysDetails)
            val points = ArrayList<Entry>()
            for (i in item.days) {
                points.add(Entry(i.day!!.toFloat(), i.MAX!!.toFloat()))
            }
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
            dataSet.setDrawCircleHole(true)
            dataSet.valueTextSize = 0f
            //dataSet.setDrawFilled(true)
            //dataSet.setDrawCircles(false)

            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(dataSet)
            val data = LineData(dataSets)

            // set data
            binding.lineChart.data = data
            binding.lineChart.description.isEnabled = true
            binding.lineChart.description.text = "Monthly"
            binding.lineChart.legend.isEnabled = false
            binding.lineChart.setPinchZoom(true)
            binding.lineChart.xAxis.enableGridDashedLine(5f, 5f, 0f)
            binding.lineChart.axisLeft.enableGridDashedLine(5f, 5f, 0f)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGraphHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemChangedListener {
        fun onItemChanged(days: ArrayList<DaysDetails>)
    }
}