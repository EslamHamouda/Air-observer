package com.example.airobserver.ui.home.history_fragment

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.airobserver.databinding.ListItemDetailedReadingsBinding
import com.example.airobserver.domain.model.response.AqiHistoryDetailsResponse
import com.example.airobserver.domain.model.response.AqiHistoryResponse
import com.example.airobserver.domain.model.response.DaysDetails

class DetailedReadingsAdapter(
   private val list: List<AqiHistoryDetailsResponse>
) : RecyclerView.Adapter<DetailedReadingsAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ListItemDetailedReadingsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AqiHistoryDetailsResponse) {

            binding.tvTime.text = item.pollutantDate
            binding.tvAqi.text = "AQI: ${item.MAX} | ${item.Category}"

            setDetailedAqi(item.PM10!!.toDouble(),binding.tvPm25) // setTextColor and it depends on the value that is retrieved from database

            setDetailedAqi(item.PM10!!.toDouble(),binding.tvPm10) // setTextColor and it depends on the value that is retrieved from database

            setDetailedAqi(item.CO!!.toDouble(),binding.tvCo) // setTextColor and it depends on the value that is retrieved from database

            setDetailedAqi(item.O3!!.toDouble(),binding.tvO3) // setTextColor and it depends on the value that is retrieved from database

            setDetailedAqi(item.SO2!!.toDouble(),binding.tvSo2) // setTextColor and it depends on the value that is retrieved from database

            setDetailedAqi(item.NO2!!.toDouble(),binding.tvNo2) // setTextColor and it depends on the value that is retrieved from database

            setDetailedAqi(item.MAX!!.toDouble(),binding.tvAqi) // setTextColor and it depends on the value that is retrieved from database
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemDetailedReadingsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private fun setDetailedAqi(value: Double, txt: TextView) {
        when (value) {
            in 0.0 .. 50.0 -> txt.setTextColor(Color.parseColor("#488A5A"))
            in 51.0 .. 100.0 -> txt.setTextColor(Color.parseColor("#ddad25"))
            in 101.0 .. 150.0 -> txt.setTextColor(Color.parseColor("#fc5b00"))
            in 151.0 .. 200.0 -> txt.setTextColor(Color.parseColor("#c72c2c"))
            in 201.0 .. 300.0 -> txt.setTextColor(Color.parseColor("#6A359C"))
            in 301.0 .. 500.0 -> txt.setTextColor(Color.parseColor("#800000"))
        }
    }

}