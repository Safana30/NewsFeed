package com.example.newsfeed.utils

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.HttpURLConnection
import java.net.URL


suspend fun getLocalImagePath(context: Context, url: String): String? {
    val fileName = url.hashCode().toString() + ".jpg"
    val file = File(context.filesDir, fileName)

    return if (file.exists()) {
        file.absolutePath
    } else {
        try {
            val connection = withContext(Dispatchers.IO) {
                URL(url).openConnection()
            } as HttpURLConnection
            withContext(Dispatchers.IO) {
                connection.connect()
            }
            val inputStream = connection.inputStream
            file.outputStream().use { inputStream.copyTo(it) }
            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}