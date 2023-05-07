package com.example.githubrepoviewer.services

import com.example.githubrepoviewer.model.response.RepoListResponses
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RepoAPI {

    @GET("search/repositories")
    suspend fun getPublicRepoList(
        @Query("q") q: String?,
        @Query("page") page: Int?,
        @Query("sort") sort: String?
    ): Response<RepoListResponses>

}