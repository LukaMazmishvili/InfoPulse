package com.example.infopulse.ui.saved.savedNews

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.infopulse.base.BaseFragment
import com.example.infopulse.data.remote.model.Article
import com.example.infopulse.databinding.FragmentSavedNewsBinding
import com.example.infopulse.ui.saved.SavedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SavedNewsFragment :
    BaseFragment<FragmentSavedNewsBinding>(FragmentSavedNewsBinding::inflate) {

    private val viewModel: SavedViewModel by viewModels()

    private val adapter: SavedNewsAdapter by lazy {
        SavedNewsAdapter()
    }

    override fun started() {

    }

    private fun updateUI(list: List<Article>) {
        binding.root.adapter = adapter
        adapter.submitList(list)
    }

    override fun listeners() {
        adapter.onDeleteItemClicked = {
            val builder = AlertDialog.Builder(requireActivity())
            builder.setTitle("Delete News!")
            builder.setMessage("Are you sure?")
            builder.setIcon(android.R.drawable.ic_dialog_alert)

            builder.setPositiveButton("Yes"){dialogInterface, which ->
                viewModel.deleteArticle(it)
                updateUI(viewModel.readSavedArticles())
            }

            builder.setNegativeButton("No"){dialogInterface, which ->
            }

            val alertDialog: AlertDialog = builder.create()

            alertDialog.setCancelable(false)
            alertDialog.show()
        }
    }

    override fun observer() {
        updateUI(viewModel.readSavedArticles())
    }

}