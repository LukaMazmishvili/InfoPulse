package com.example.infopulse.ui.saved.savedSources

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.infopulse.data.remote.model.Article
import com.example.infopulse.data.remote.model.SourcesModelDto
import com.example.infopulse.databinding.ItemSavedSourceBinding
import com.example.infopulse.utils.GlobalItemDiffUtil

class SavedSourcesAdapter : ListAdapter<SourcesModelDto.Source, SavedSourcesAdapter.ViewHolder>(
    GlobalItemDiffUtil<SourcesModelDto.Source>()
) {

    var onItemClick: ((Article) -> Unit)? = null
    var onDeleteItemClicked: ((SourcesModelDto.Source) -> Unit)? = null

    inner class ViewHolder(private val binding: ItemSavedSourceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SourcesModelDto.Source) {
            with(binding) {
                tvCategory.text = item.category
                tvSourceName.text = item.name
                tvDescription.text = item.description
                tvCountryAndLang.text = "${item.country}/${item.language}"
                btnRemoveSource.setOnClickListener {
                    onDeleteItemClicked?.invoke(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemSavedSourceBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)
    }
}