package com.example.infopulse.ui.saved

import androidx.lifecycle.ViewModel
import com.example.infopulse.data.local.DBHelper
import com.example.infopulse.data.remote.model.Article
import com.example.infopulse.data.remote.model.ArticlesModelDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(private val db: DBHelper) : ViewModel() {

    fun readSavedArticles() = db.getAllNews()

    fun deleteArticle(item: Article) = db.deleteNews(item)

}