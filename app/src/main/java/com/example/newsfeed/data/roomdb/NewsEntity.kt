package com.example.newsfeed.data.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity (
    @PrimaryKey val id:String,
    val author: String ?=null,
    val content: String ?=null,
    val description: String ?=null,
    val publishedAt: String ?=null,
    val title: String ,
    val url: String ?=null,
    val urlToImage: String ?=null
)