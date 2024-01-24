package com.example.infopulse.ui.sources

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.infopulse.data.remote.model.ArticlesModelDto
import com.example.infopulse.data.remote.model.SourcesModelDto
import com.example.infopulse.databinding.ItemSourceBinding
import com.example.infopulse.domain.repository.SourcesRepository
import com.example.infopulse.utils.GlobalItemDiffUtil

class SourcesAdapter :
    ListAdapter<SourcesModelDto.Source, SourcesAdapter.ViewHolder>(GlobalItemDiffUtil<SourcesModelDto.Source>()) {

    inner class ViewHolder(private val binding: ItemSourceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : SourcesModelDto.Source){
            with(binding){
                tvCategory.text = item.category
                tvSourceName.text = item.name
                tvDescription.text = item.description
                tvCountryAndLang.text = "${item.country}/${item.language}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemSourceBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

//    class SourcesDiffUtil() : DiffUtil.ItemCallback<SourcesModelDto.Source>() {
//        override fun areItemsTheSame(
//            oldItem: SourcesModelDto.Source,
//            newItem: SourcesModelDto.Source
//        ): Boolean {
//            return oldItem === newItem
//        }
//
//        override fun areContentsTheSame(
//            oldItem: SourcesModelDto.Source,
//            newItem: SourcesModelDto.Source
//        ): Boolean {
//            return oldItem == newItem
//        }
//    }

}