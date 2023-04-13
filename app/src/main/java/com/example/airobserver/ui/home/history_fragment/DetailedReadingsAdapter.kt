package com.example.airobserver.ui.home.history_fragment

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.airobserver.databinding.ListItemDetailedReadingsBinding
import com.example.airobserver.databinding.ListItemGasBinding
import com.example.airobserver.domain.model.gasmodel

class DetailedReadingsAdapter(
   private val list: List<String>
) : RecyclerView.Adapter<DetailedReadingsAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ListItemDetailedReadingsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.tvPm25.text = 25.toString()
            binding.tvPm10.text = 10.toString()
            binding.tvCo.text = 2.toString()
            binding.tvO3.text = 10.toString()
            binding.tvSo2.text = 20.toString()
            binding.tvNo2.text = 10.toString()
            binding.tvTime.text = "5/20"
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