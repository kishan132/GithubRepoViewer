package com.example.githubrepoviewer.data

import com.example.githubrepoviewer.GithubClient
import com.example.githubrepoviewer.model.entities.Item

object RepoData {

    suspend fun getUserRepoList(query:String,page:Int,sortType:String) : List<Item>? {
        val response = GithubClient.api.getPublicRepoList(q = query,page = page,sort= sortType)
        return response.body()?.items
    }

}