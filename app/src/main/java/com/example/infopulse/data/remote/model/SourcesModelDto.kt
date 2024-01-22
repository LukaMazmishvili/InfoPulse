package com.example.infopulse.data.remote.model

data class SourcesModelDto(
    val status: String,
    val sources: List<Source>
) {
    data class Source(
        val id: String,
        val name: String,
        val description: String,
        val url: String,
        val category: String,
        val language: String,
        val country: String
    )
}