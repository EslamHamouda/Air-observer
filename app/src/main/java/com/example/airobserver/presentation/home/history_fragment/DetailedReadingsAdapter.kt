package com.example.airobserver.presentation.home.history_fragment

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.airobserver.R
import com.example.airobserver.databinding.ListItemDetailedReadingsBinding
import com.example.airobserver.domain.model.response.AqiHistoryDetailsResponse

class DetailedReadingsAdapter(
   private val list: List<AqiHistoryDetailsResponse>
) : RecyclerView.Adapter<DetailedReadingsAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ListItemDetailedReadingsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AqiHistoryDetailsResponse) {

            binding.tvTime.text = item.pollutantDate
            binding.tvAqi.text = "AQI: ${item.MAX} | ${item.Category}"

            binding.tvPm25.text = item.PM25
            setDetailedAqi(item.PM25!!.toDouble(),binding.tvPm25) // setTextColor and it depends on the value that is retrieved from database

            binding.tvPm10.text = item.PM10
            setDetailedAqi(item.PM10!!.toDouble(),binding.tvPm10) // setTextColor and it depends on the value that is retrieved from database

            binding.tvCo.text = item.CO
            setDetailedAqi(item.CO!!.toDouble(),binding.tvCo) // setTextColor and it depends on the value that is retrieved from database

            binding.tvO3.text = item.O3
            setDetailedAqi(item.O3!!.toDouble(),binding.tvO3) // setTextColor and it depends on the value that is retrieved from database

            binding.tvSo2.text = item.SO2
            setDetailedAqi(item.SO2!!.toDouble(),binding.tvSo2) // setTextColor and it depends on the value that is retrieved from database

            binding.tvNo2.text = item.NO2
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

}