package com.example.infopulse.ui.newsFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.infopulse.R
import com.example.infopulse.base.BaseFragment
import com.example.infopulse.databinding.FragmentNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
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