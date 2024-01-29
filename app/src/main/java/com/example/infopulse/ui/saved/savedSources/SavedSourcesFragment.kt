package com.example.infopulse.ui.saved.savedSources

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.infopulse.R
import com.example.infopulse.base.BaseFragment
import com.example.infopulse.data.remote.model.SourcesModelDto
import com.example.infopulse.databinding.FragmentSavedSourcesBinding
import com.example.infopulse.ui.saved.SavedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SavedSourcesFragment :
    BaseFragment<FragmentSavedSourcesBinding>(FragmentSavedSourcesBinding::inflate) {

    private val viewModel: SavedViewModel by viewModels()

    private val adapter: SavedSourcesAdapter by lazy {
        SavedSourcesAdapter()
    }

    override fun started() {
        setupViews()
    }

    private fun setupViews() {
        binding.root.adapter = adapter
    }

    override fun listeners() {
        adapter.onDeleteItemClicked = { item ->
            val builder = AlertDialog.Builder(requireActivity())
            builder.setTitle("Remove Article From Favourites?")
            builder.setMessage("Are you sure?")
            builder.setIcon(android.R.drawable.ic_dialog_alert)

            builder.setPositiveButton("Yes") { dialogInterface, which ->
                viewModel.deleteSource(item)
                updateUi(viewModel.readSavedSources())
            }

            builder.setNegativeButton("No") { dialogInterface, which ->
            }

            val alertDialog: AlertDialog = builder.create()

            alertDialog.setCancelable(false)
            alertDialog.show()
        }
    }

    override fun observer() {
        updateUi(viewModel.readSavedSources())
    }

    private fun updateUi(list: List<SourcesModelDto.Source>){
        adapter.submitList(list)
    }


}