package com.example.infopulse.ui.newsFragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infopulse.common.Resource
import com.example.infopulse.data.remote.model.ArticlesModelDto
import com.example.infopulse.domain.repository.ArticlesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsFragmentVM @Inject constructor(private val articlesRepository: ArticlesRepository) :
    ViewModel() {

    private val _getArticlesState = MutableStateFlow(ArticlesApiState())
    val getArticlesState = _getArticlesState.asStateFlow()

    fun getArticles() {
        viewModelScope.launch {
            _getArticlesState.value = getArticlesState.value.copy(isLoading = true)
            when (val response = articlesRepository.fetchArticles()) {
                is Resource.Success -> {
                    _getArticlesState.value = getArticlesState.value.copy(
                        isLoading = false,
                        data = response.data.articles,
                    )
                    Log.d("CheckIfCodeWorks", "getChats: $response")
                }

                is Resource.Error -> {
                    _getArticlesState.value = _getArticlesState.value.copy(
                        isLoading = false,
                        error = response.errorMsg
                    )
                    Log.d("CheckIfCodeWorks", "getChats: $response")
                }

                is Resource.Loading -> {
                    _getArticlesState.value = _getArticlesState.value.copy(
                        isLoading = true
                    )
                    Log.d("CheckIfCodeWorks", "getChats: $response")
                }
            }
        }

    }

    data class ArticlesApiState(
        val isLoading: Boolean = false,
        val data: List<ArticlesModelDto.Article>? = null,
        val error: String = ""
    )
}