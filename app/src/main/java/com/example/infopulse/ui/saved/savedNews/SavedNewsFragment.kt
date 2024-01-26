package com.example.infopulse.ui.saved.savedNews

import android.util.Log
import androidx.fragment.app.viewModels
import com.example.infopulse.base.BaseFragment
import com.example.infopulse.databinding.FragmentSavedNewsBinding
import com.example.infopulse.ui.saved.SavedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedNewsFragment :
    BaseFragment<FragmentSavedNewsBinding>(FragmentSavedNewsBinding::inflate) {

    private val viewModel: SavedViewModel by viewModels()

    private val adapter: SavedNewsAdapter by lazy {
        SavedNewsAdapter()
    }

    override fun started() {
        updateUI()

    }

    private fun updateUI() {
        binding.root.adapter = adapter
        adapter.submitList(viewModel.readSavedArticles())
    }

    override fun listeners() {
        adapter.onDeleteItemClicked = {
            viewModel.deleteArticle(it)
        }
    }

}