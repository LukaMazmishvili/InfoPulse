package com.example.infopulse.ui.news

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.infopulse.data.remote.model.Article
import com.example.infopulse.databinding.ItemArticleBinding
import com.example.infopulse.extensions.formatDate
import com.example.infopulse.extensions.uploadImage
import com.example.infopulse.utils.GlobalItemDiffUtil

class ArticlesAdapter() :
    ListAdapter<Article, ArticlesAdapter.ViewHolder>(GlobalItemDiffUtil<Article>()) {


    var onItemClick: ((Article) -> Unit)? = null
    var saveItemClicked: ((Article) -> Unit)? = null

    inner class ViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Article) {
            item.urlToImage?.let {
                binding.sivArticleImage.uploadImage(item.urlToImage)
            }
            binding.tvTitle.text = item.title
            binding.tvDescription.text = item.content
            binding.tvSourceAndAuthor.text = item.source?.name.plus(", ${item.author}")
            binding.tvPublishDate.text = item.publishedAt.formatDate()
            binding.root.setOnClickListener {
                Log.d("ItemInAdapter", "onBindViewHolder: $item")
                onItemClick?.invoke(item)
            }
            binding.btnSaveNews.setOnClickListener {
                saveItemClicked?.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemArticleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)
    }
}