package com.example.infopulse.ui.sources

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.infopulse.base.BaseFragment
import com.example.infopulse.databinding.FragmentSourcesBinding
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
        setUpViews()
    }

    override fun listeners() {

    }

    private fun setUpViews() {
        with(binding) {
            rvSources.adapter = sourcesAdapter
        }
    }

    override fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getSources()

                viewModel.getSourcesState.collect {
                    val data = it.data
                    Log.d("dataInFragment", "observer: ${data}")
                    sourcesAdapter.submitList(data)
                }
            }
        }
    }

}