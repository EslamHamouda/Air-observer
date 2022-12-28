package com.example.airobserver.ui.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.airobserver.databinding.OnboardingSliderBinding

class ViewPagerAdapter(
    val context: Context,
    val images:List<Int>,
    val headings:List<Int>,
    val description:List<Int>
) : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    class ViewHolder(private val binding: OnboardingSliderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageItem:Int, headingItem:Int,descriptionItem:Int) {
            binding.titleImage.setImageResource(imageItem)
            binding.titleText.setText(headingItem)
            binding.titleDescription.setText(descriptionItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = OnboardingSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position], headings[position], description[position])
    }

    override fun getItemCount(): Int {
        return description.size
    }
}