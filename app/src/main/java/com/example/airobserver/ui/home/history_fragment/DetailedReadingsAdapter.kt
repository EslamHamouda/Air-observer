package com.example.airobserver.ui.home.history_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.airobserver.databinding.ListItemDetailedReadingsBinding
import com.example.airobserver.domain.model.response.AqiHistoryResponse

class DetailedReadingsAdapter(
   private val list: List<AqiHistoryResponse>
) : RecyclerView.Adapter<DetailedReadingsAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ListItemDetailedReadingsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AqiHistoryResponse) {
            binding.tvPm25.text = item.PM10
            binding.tvPm10.text = item.PM10
            binding.tvCo.text = item.CO
            binding.tvO3.text = item.O3
            binding.tvSo2.text = item.SO2
            binding.tvNo2.text = item.NO2
            binding.tvTime.text = item.pollutantDate
            binding.tvAqi.text = "AQI: ${item.MAX} | ${item.Category}"
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

}