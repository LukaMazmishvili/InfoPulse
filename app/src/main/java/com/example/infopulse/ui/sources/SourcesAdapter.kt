package com.example.infopulse.ui.sources

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.infopulse.data.remote.model.SourcesModelDto
import com.example.infopulse.databinding.ItemSourceBinding
import com.example.infopulse.utils.GlobalItemDiffUtil

class SourcesAdapter :
    ListAdapter<SourcesModelDto.Source, SourcesAdapter.ViewHolder>(GlobalItemDiffUtil<SourcesModelDto.Source>()) {

    var onAddToFavouritesClick: ((SourcesModelDto.Source) -> Unit)? = null

    inner class ViewHolder(private val binding: ItemSourceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SourcesModelDto.Source) {
            with(binding) {
                tvCategory.text = item.category
                tvSourceName.text = item.name
                tvDescription.text = item.description
                tvCountryAndLang.text = "${item.country}/${item.language}"
                btnAddToFavourites.setOnClickListener {
                    onAddToFavouritesClick?.invoke(item)
                }
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