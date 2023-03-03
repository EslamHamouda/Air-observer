package com.example.airobserver.ui.home.home_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.airobserver.databinding.ListItemGasBinding
import com.example.airobserver.domain.model.gasmodel

class DetailedReadingsAdapter(
    var list: List<gasmodel>
) : RecyclerView.Adapter<DetailedReadingsAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ListItemGasBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: gasmodel) {
            binding.tvGasPercentage.text = item.value.toString()
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