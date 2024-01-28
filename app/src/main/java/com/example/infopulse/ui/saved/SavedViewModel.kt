package com.example.infopulse.ui.saved

import androidx.lifecycle.ViewModel
import com.example.infopulse.data.local.DBHelper
import com.example.infopulse.data.remote.model.Article
import com.example.infopulse.data.remote.model.ArticlesModelDto
import com.example.infopulse.data.remote.model.SourcesModelDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(private val db: DBHelper) : ViewModel() {

    fun readSavedArticles() = db.getAllNews()

    fun readSavedSources() = db.getAllSources()

    fun deleteArticle(item: Article) = db.deleteNews(item)
    fun deleteSource(item: SourcesModelDto.Source) = db.deleteSource(item)

}