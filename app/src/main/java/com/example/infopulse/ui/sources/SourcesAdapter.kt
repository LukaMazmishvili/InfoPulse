package com.example.infopulse.ui.sources

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.infopulse.data.remote.model.SourcesModelDto
import com.example.infopulse.databinding.ItemSourceBinding

class SourcesAdapter :
    ListAdapter<SourcesModelDto.Source, SourcesAdapter.ViewHolder>(SourcesDiffUtil()) {

    inner class ViewHolder(binding: ItemSourceBinding) : RecyclerView.ViewHolder(binding.root) {
        val category = binding.tvCategory
        val name = binding.tvSourceName
        val description = binding.tvDescription
        val countryAndLanguage = binding.tvCountryAndLang
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemSourceBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        with(holder) {
            category.text = item.category
            name.text = item.name
            description.text = item.description
            countryAndLanguage.text = "${item.country}/${item.language}"
        }
    }

    class SourcesDiffUtil() : DiffUtil.ItemCallback<SourcesModelDto.Source>() {
        override fun areItemsTheSame(
            oldItem: SourcesModelDto.Source,
            newItem: SourcesModelDto.Source
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: SourcesModelDto.Source,
            newItem: SourcesModelDto.Source
        ): Boolean {
            return oldItem == newItem
        }
    }

}