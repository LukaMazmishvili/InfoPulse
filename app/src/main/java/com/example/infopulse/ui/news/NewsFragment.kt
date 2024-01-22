package com.example.infopulse.ui.news

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.infopulse.base.BaseFragment
import com.example.infopulse.databinding.FragmentNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : BaseFragment<FragmentNewsBinding>(
    FragmentNewsBinding::inflate
) {

    private val viewModel: NewsFragmentVM by viewModels()

    private val articlesAdapter: ArticlesAdapter by lazy {
        ArticlesAdapter()
    }

    override fun started() {
        setUpViews()
    }

    override fun listeners() {

    }

    private fun setUpViews() {
        with(binding) {
            rvArticles.adapter = articlesAdapter
        }
    }

    override fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getArticles()

                viewModel.getArticlesState.collect {
                    val data = it.data
                    Log.d("dataInFragment", "observer: ${data}")
                    articlesAdapter.submitList(data)
                }
            }
        }

    }

}