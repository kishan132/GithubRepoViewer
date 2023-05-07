package com.example.githubrepoviewer.model.response


import com.example.githubrepoviewer.model.entities.Item
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepoListResponses(
    @Json(name = "incomplete_results")
    val incompleteResults: Boolean?,
    @Json(name = "items")
    val items: List<Item>?,
    @Json(name = "total_count")
    val totalCount: Int?
)