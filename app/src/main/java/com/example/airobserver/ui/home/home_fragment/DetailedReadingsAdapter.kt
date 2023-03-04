package com.example.airobserver.ui.home.home_fragment

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.airobserver.databinding.ListItemGasBinding
import com.example.airobserver.domain.model.gasmodel

class DetailedReadingsAdapter(
   private val list: List<gasmodel>
) : RecyclerView.Adapter<DetailedReadingsAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ListItemGasBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: gasmodel) {
            binding.tvGas.text=item.abs
            binding.tvGasName.text=item.name
            binding.tvGasPercentage.text = item.value.toString()
            val color=ColorStateList.valueOf(Color.parseColor(item.color))
            binding.todayAqiColor.backgroundTintList=color
            binding.tvGasFeedback.text=item.status
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemGasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}