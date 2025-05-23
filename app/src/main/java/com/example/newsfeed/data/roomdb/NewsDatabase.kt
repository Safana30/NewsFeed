package com.example.newsfeed.data.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities =[NewsEntity::class], version = 1)
abstract class NewsDatabase :RoomDatabase() {

    abstract fun NewsDao() : NewsDao
}