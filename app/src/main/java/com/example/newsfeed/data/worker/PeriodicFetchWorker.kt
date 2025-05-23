package com.example.newsfeed.data.worker

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.newsfeed.data.apiservice.ApiInterface
import com.example.newsfeed.data.apiservice.apimodels.Articles
import com.example.newsfeed.data.roomdb.NewsDao
import com.example.newsfeed.domain.models.ArticleBaseModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltWorker
class PeriodicFetchWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val apiInterface: ApiInterface,
    private val newsDao: NewsDao

):CoroutineWorker(appContext,workerParams) {
    @RequiresApi(Build.VERSION_CODES.O)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    @RequiresApi(Build.VERSION_CODES.O)
    val fromDate = LocalDate.now().minusWeeks(1).format(formatter)

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        return try {
            val response = apiInterface.getNews(query = "tesla", from = fromDate)
            if (response.isSuccessful) {
                val articles = response.body()?.articles ?: emptyList()

                val entityList = articles.map { ArticleBaseModel.fromAPIModel(it).toEntity() }
                newsDao.insertNews(entityList)

                Result.success()
            } else {
                Result.retry()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }
}
