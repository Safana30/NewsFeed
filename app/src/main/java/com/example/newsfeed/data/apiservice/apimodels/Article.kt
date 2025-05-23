package com.example.newsfeed.data.apiservice.apimodels

data class Article(
    val author: String ?=null,
    val content: String ?=null,
    val description: String ?=null,
    val publishedAt: String ?=null,
    val title: String,
    val url: String ?=null,
    val urlToImage: String ?=null
)