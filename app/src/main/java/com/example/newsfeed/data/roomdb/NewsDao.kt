package com.example.newsfeed.data.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import kotlinx.coroutines.flow.Flow
import androidx.room.Query

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(newsList: List<NewsEntity>)

    @Query("SELECT * FROM news ")
    fun getNews(): Flow<List<NewsEntity>>


}