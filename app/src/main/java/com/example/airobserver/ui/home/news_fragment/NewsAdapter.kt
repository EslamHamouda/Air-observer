package com.example.airobserver.ui.home.news_fragment

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.airobserver.R
import com.example.airobserver.databinding.ListItemGasBinding
import com.example.airobserver.databinding.ListItemNewsBinding
import com.example.airobserver.domain.model.gasmodel
import com.example.airobserver.domain.model.response.Articles
import com.example.airobserver.utils.dateFormat

class NewsAdapter(
   private val list: List<Articles>
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ListItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Articles) {
            Glide.with(binding.ivNews.context)
                .load(item.urlToImage)
                .fitCenter()
                .placeholder(R.drawable.ic_news)
                .error(R.drawable.ic_news)
                .into(binding.ivNews)
            binding.tvNews.text=item.title
            binding.tvSourceName.text=item.source?.name
            binding.tvTime.text= dateFormat(item.publishedAt)
            binding.root.setOnClickListener {
                it.findNavController().navigate(NewsFragmentDirections.actionNewsFragmentToNewsDetailFragment(item.url!!))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}