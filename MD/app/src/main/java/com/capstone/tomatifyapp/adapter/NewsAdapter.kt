package com.capstone.tomatifyapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.tomatifyapp.R
import com.capstone.tomatifyapp.model.NewsItem

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private val newsItems: MutableList<NewsItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsItem = newsItems[position]
        holder.bind(newsItem)
    }

    override fun getItemCount(): Int {
        return newsItems.size
    }

    fun setNewsItems(items: List<NewsItem>) {
        newsItems.clear()
        newsItems.addAll(items)
        notifyDataSetChanged()
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.tv_news_title)
        private val contentTextView: TextView = itemView.findViewById(R.id.tv_news_desc)

        fun bind(newsItem: NewsItem) {
            titleTextView.text = newsItem.title
            contentTextView.text = newsItem.excerpt
        }
    }
}

