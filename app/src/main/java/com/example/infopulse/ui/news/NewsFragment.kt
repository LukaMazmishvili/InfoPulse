package com.example.infopulse.ui.news

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.infopulse.R
import com.example.infopulse.base.BaseFragment
import com.example.infopulse.databinding.FragmentNewsBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
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

        viewModel.getArticles()

        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavBar).visibility =
            View.VISIBLE

        setUpViews()
    }

    override fun listeners() {
        binding.root.setOnRefreshListener {
            observer()
        }

        articlesAdapter.onItemClick = {
            findNavController().navigate(
                NewsFragmentDirections.actionNewsFragmentToArticlesFragment(
                    it
                )
            )
        }
    }

    private fun setUpViews() {
        with(binding) {
            rvArticles.adapter = articlesAdapter
        }
    }

    override fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getArticlesState.collect {
                    updateUi(it)
                }
            }
        }
    }

    private fun updateUi(apiState: NewsFragmentVM.ArticlesApiState) {
        articlesAdapter.submitList(apiState.data)
        binding.root.isRefreshing = apiState.isLoading

        Log.d("LoadingCallback", "getArticles: ${apiState.isLoading}")
        if (apiState.error.isNotEmpty()) {
            Toast.makeText(requireActivity(), apiState.error, Toast.LENGTH_SHORT).show()
        }
    }

}