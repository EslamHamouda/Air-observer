package com.example.airobserver.ui.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.airobserver.databinding.ItemOnboardingSliderBinding

class ViewPagerAdapter(
    private val images:List<Int>,
    private val headings:List<Int>,
    private val description:List<Int>
) : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemOnboardingSliderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageItem:Int, headingItem:Int,descriptionItem:Int) {
            binding.ivSlider.setImageResource(imageItem)
            binding.tvTitle.setText(headingItem)
            binding.tvDescription.setText(descriptionItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOnboardingSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position], headings[position], description[position])
    }

    override fun getItemCount(): Int {
        return description.size
    }
}