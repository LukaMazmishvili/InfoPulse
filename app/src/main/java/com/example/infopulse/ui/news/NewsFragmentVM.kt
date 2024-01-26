package com.example.infopulse.ui.news

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infopulse.common.Resource
import com.example.infopulse.data.local.DBHelper
import com.example.infopulse.data.remote.model.Article
import com.example.infopulse.data.remote.model.ArticlesModelDto
import com.example.infopulse.domain.repository.ArticlesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsFragmentVM @Inject constructor(
    private val articlesRepository: ArticlesRepository,
    private val db: DBHelper
) :
    ViewModel() {

    private val _getArticlesState = MutableStateFlow(ArticlesApiState())
    val getArticlesState = _getArticlesState.asStateFlow()

    init {
        getArticles()
    }

    fun saveArticle(item: Article) {
        db.insertNews(item)
    }

    private fun getArticles() {
        viewModelScope.launch {
            _getArticlesState.value = getArticlesState.value.copy(isLoading = true)
            when (val response = articlesRepository.fetchArticles()) {
                is Resource.Success -> {
                    _getArticlesState.value = getArticlesState.value.copy(
                        isLoading = false,
                        data = response.data.articles,
                    )
                }

                is Resource.Error -> {
                    _getArticlesState.value = getArticlesState.value.copy(
                        isLoading = false,
                        error = response.errorMsg
                    )
                }

                is Resource.Loading -> {
                    _getArticlesState.value = getArticlesState.value.copy(
                        isLoading = true
                    )
                }
            }
        }
    }

    data class ArticlesApiState(
        val isLoading: Boolean = false,
        val data: List<Article>? = null,
        val error: String = ""
    )
}