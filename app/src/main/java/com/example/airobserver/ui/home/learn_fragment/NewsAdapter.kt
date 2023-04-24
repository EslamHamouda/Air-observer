package com.example.airobserver.ui.home.learn_fragment

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.airobserver.R
import com.example.airobserver.databinding.ListItemNewsBinding
import com.example.airobserver.domain.model.response.Articles
import com.example.airobserver.utils.convertTo12HourAndDateFormat

class NewsAdapter(
   private val list: List<Articles>,
   private val listener: OnItemClickListener

) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ListItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: Articles) {
            Glide.with(binding.ivNews.context)
                .load(item.urlToImage)
                .fitCenter()
                .placeholder(R.drawable.ic_news)
                .error(R.drawable.ic_news)
                .into(binding.ivNews)
            binding.tvNews.text=item.title
            binding.tvDetails.text=item.description
            binding.tvTime.text= convertTo12HourAndDateFormat(item.publishedAt)
            binding.root.setOnClickListener {
                listener.onItemClick(item)
                //it.findNavController().navigate(NewsFragmentDirections.actionNewsFragmentToNewsDetailFragment(item.url!!))
            }
            binding.ivShare.setOnClickListener {
                listener.onItemClickShare(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener {
        fun onItemClick(item: Articles)
        fun onItemClickShare(item: Articles)
    }
}