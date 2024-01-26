package com.example.infopulse.ui.article

import android.view.View
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.infopulse.R
import com.example.infopulse.base.BaseFragment
import com.example.infopulse.databinding.FragmentArticlesBinding
import com.example.infopulse.extensions.formatDate
import com.google.android.material.bottomnavigation.BottomNavigationView

class ArticlesFragment : BaseFragment<FragmentArticlesBinding>(FragmentArticlesBinding::inflate) {

    private val args: ArticlesFragmentArgs by navArgs()

    override fun started() {

        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavBar).visibility =
            View.GONE

        updateUi()
    }

    override fun listeners() {

    }

    private fun updateUi() {
        val data = args.articleData

        with(binding) {
            tvAuthor.text = data.author
            tvTitle.text = data.title
            tvPublishDate.text = data.publishedAt.formatDate()
            tvDescription.text = data.content
            data.urlToImage?.let {
                Glide.with(sivArticleImage).load(data.urlToImage).into(sivArticleImage)
            }
        }

    }


}