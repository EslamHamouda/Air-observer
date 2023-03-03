package com.example.airobserver.ui.home.news_fragment

import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.airobserver.databinding.ListItemNewsBinding

class NewsAdapter(private val List:ArrayList<News>):RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = ListItemNewsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(List[position])

    }

    override fun getItemCount(): Int {
        return List.size
    }

    class ViewHolder(private val binding: ListItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item:News){
            binding.tvNews.text=item.Title
            binding.imNews.setImageResource(item.Image)
        }
    }
}