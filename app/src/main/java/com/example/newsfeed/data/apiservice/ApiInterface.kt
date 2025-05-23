package com.example.newsfeed.data.apiservice

import com.example.newsfeed.data.apiservice.apimodels.Articles
import com.example.newsfeed.utils.EVERYTHING
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET(EVERYTHING)
    suspend fun getNews(
        @Query("q") query: String,
        @Query("from") from: String,
        @Query("sortBy") sortBy: String = "publishedAt"
    ) : Response<Articles>
}