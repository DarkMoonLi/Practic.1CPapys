package com.example.newsblock.scenes.newsBlock

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.newsblock.databinding.ItemNewsLayoutBinding
import com.example.newsblock.models.NewX


class NewsAdapter(private val onClickListener: OnItemClickListener): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>()
{
    private var listNews: MutableList<NewX> = mutableListOf()

    class NewsViewHolder(val binding: ItemNewsLayoutBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = ItemNewsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.binding.itemTitle.text = listNews[position].title
        holder.binding.itemAnnotation.text = listNews[position].annotation
        holder.binding.root.setOnClickListener {
            onClickListener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return listNews.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: MutableList<NewX>?)
    {
        if (list != null) {
            listNews = list
        };
        notifyDataSetChanged();
    }
}
