package com.example.infopulse.ui.news

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.infopulse.data.remote.model.Article
import com.example.infopulse.data.remote.model.ArticlesModelDto
import com.example.infopulse.databinding.ItemArticleBinding
import com.example.infopulse.extentions.formatDate
import com.example.infopulse.utils.GlobalItemDiffUtil

class ArticlesAdapter() :
    ListAdapter<Article, ArticlesAdapter.ViewHolder>(GlobalItemDiffUtil<Article>()) {


    var onItemClick: ((Article) -> Unit)? = null

    inner class ViewHolder(binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.sivArticleImage
        val title = binding.tvTitle
        val description = binding.tvDescription
        val sourceAndAuthor = binding.tvSourceAndAuthor
        val publishDate = binding.tvPublishDate
        val root = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemArticleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        with(holder) {
            item.urlToImage?.let {
                Glide.with(image).load(item.urlToImage).into(image)
            }
            description.text = item.content
            title.text = item.title
            publishDate.text = item.publishedAt.formatDate()
            sourceAndAuthor.text = item.source.name.plus(", ${item.author}")

            holder.root.setOnClickListener {
                Log.d("ItemInAdapter", "onBindViewHolder: $item")
                onItemClick?.invoke(item)
            }
        }
    }

//    class ArticlesDiffUtil() : DiffUtil.ItemCallback<ArticlesModelDto.Article>() {
//        override fun areItemsTheSame(
//            oldItem: ArticlesModelDto.Article,
//            newItem: ArticlesModelDto.Article
//        ): Boolean {
//            return oldItem === newItem
//        }
//
//        override fun areContentsTheSame(
//            oldItem: ArticlesModelDto.Article,
//            newItem: ArticlesModelDto.Article
//        ): Boolean {
//            return oldItem == newItem
//        }
//    }
}