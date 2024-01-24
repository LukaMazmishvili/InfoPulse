package com.example.infopulse.ui.sources

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infopulse.common.Resource
import com.example.infopulse.data.remote.model.SourcesModelDto
import com.example.infopulse.domain.repository.SourcesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SourcesFragmentVM @Inject constructor(private val sourcesRepository: SourcesRepository) :
    ViewModel() {


    private val _getSourcesState = MutableStateFlow(SourcesApiState())
    val getSourcesState = _getSourcesState.asStateFlow()

    fun getSources() {
        viewModelScope.launch {
            _getSourcesState.value = _getSourcesState.value.copy(isLoading = true)
            when (val response = sourcesRepository.fetchSources()) {
                is Resource.Success -> {
                    _getSourcesState.value = _getSourcesState.value.copy(
                        isLoading = false,
                        data = response.data.sources,
                    )
                    Log.d("CheckIfCodeWorks", "getChats: $response")
                }

                is Resource.Error -> {
                    _getSourcesState.value = _getSourcesState.value.copy(
                        isLoading = false,
                        error = response.errorMsg
                    )
                    Log.d("CheckIfCodeWorks", "getChats: $response")
                }

                is Resource.Loading -> {
                    _getSourcesState.value = _getSourcesState.value.copy(
                        isLoading = true
                    )
                    Log.d("CheckIfCodeWorks", "getChats: $response")
                }
            }
        }

    }

    data class SourcesApiState(
        val isLoading: Boolean = false,
        val data: List<SourcesModelDto.Source>? = null,
        val error: String = ""
    )

}