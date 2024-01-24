package com.example.infopulse.ui.sources

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.infopulse.base.BaseFragment
import com.example.infopulse.databinding.FragmentSourcesBinding
import com.example.infopulse.ui.news.NewsFragmentVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SourcesFragment : BaseFragment<FragmentSourcesBinding>(
    FragmentSourcesBinding::inflate
) {

    private val viewModel: SourcesFragmentVM by viewModels()

    private val sourcesAdapter: SourcesAdapter by lazy {
        SourcesAdapter()
    }

    override fun started() {

        viewModel.getSources()

        setUpViews()
    }

    override fun listeners() {
        binding.root.setOnRefreshListener {
            sourcesAdapter.submitList(emptyList())
            observer()
        }
    }

    private fun setUpViews() {
        with(binding) {
            rvSources.adapter = sourcesAdapter
        }
    }

    override fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getSourcesState.collect {
                    updateUi(it)
                }
            }
        }
    }

    private fun updateUi(apiState: SourcesFragmentVM.SourcesApiState) {
        sourcesAdapter.submitList(apiState.data)
        binding.root.isRefreshing = apiState.isLoading
        if (apiState.error.isNotEmpty()) {
            Toast.makeText(requireActivity(), apiState.error, Toast.LENGTH_SHORT).show()
        }
    }

}