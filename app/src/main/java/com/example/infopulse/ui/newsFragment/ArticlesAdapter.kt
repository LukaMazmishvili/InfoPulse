package com.example.infopulse.ui.newsFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.infopulse.data.remote.model.ArticlesModelDto
import com.example.infopulse.databinding.ItemArticleBinding
import com.example.infopulse.extentions.formatDate

class ArticlesAdapter() :
    ListAdapter<ArticlesModelDto.Article, ArticlesAdapter.ViewHolder>(ArticlesDiffUtil()) {

    inner class ViewHolder(binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.sivArticleImage
        val title = binding.tvTitle
        val description = binding.tvDescription
        val sourceAndAuthor = binding.tvSourceAndAuthor
        val publishDate = binding.tvPublishDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemArticleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        item.urlToImage?.let {
            Glide.with(holder.image).load(item.urlToImage).into(holder.image)
        }

        holder.description.text = item.content
        holder.title.text = item.title
        holder.publishDate.text = item.publishedAt.formatDate()
        holder.sourceAndAuthor.text = item.source.name.plus(", ${item.author}")
    }

    class ArticlesDiffUtil() : DiffUtil.ItemCallback<ArticlesModelDto.Article>() {
        override fun areItemsTheSame(
            oldItem: ArticlesModelDto.Article,
            newItem: ArticlesModelDto.Article
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: ArticlesModelDto.Article,
            newItem: ArticlesModelDto.Article
        ): Boolean {
            return oldItem == newItem
        }
    }
}