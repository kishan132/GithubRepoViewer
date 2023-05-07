package com.example.githubrepoviewer

import com.example.githubrepoviewer.services.RepoAPI
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object GithubClient {

    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(10,TimeUnit.SECONDS)
        .readTimeout(10,TimeUnit.SECONDS)
        .writeTimeout(10,TimeUnit.SECONDS)

    val retrofitBuilder = Retrofit.Builder()
        .client(okHttpClient.build())
        .baseUrl("https://api.github.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val api  = retrofitBuilder.create(RepoAPI::class.java)


}