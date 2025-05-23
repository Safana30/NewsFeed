package com.example.newsfeed.data.repositoryimpl

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.newsfeed.data.apiservice.ApiInterface
import com.example.newsfeed.data.roomdb.NewsDao
import com.example.newsfeed.data.apiservice.apimodels.Article
import com.example.newsfeed.data.apiservice.apimodels.Articles
import com.example.newsfeed.data.roomdb.NewsEntity
import com.example.newsfeed.data.worker.PeriodicFetchWorker
import com.example.newsfeed.domain.models.ArticleBaseModel
import com.example.newsfeed.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface,
    private val newsDao: NewsDao,
    private val workManager: WorkManager
) : NewsRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    @RequiresApi(Build.VERSION_CODES.O)
    val fromDate = LocalDate.now().minusWeeks(1).format(formatter)

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getEveryNews(): Response<Articles> {
        return apiInterface.getNews(query = "tesla", from = fromDate)
    }

    /*override suspend fun getNewsFromDb(): ArticleBaseModel {
        return newsDao.getNews().let { ArticleBaseModel.fromEntity(it) }
    }*/
    override suspend fun getNewsFromDb(): Flow<List<ArticleBaseModel>> {
        return newsDao.getNews().map {news->
            news.map{
                ArticleBaseModel.fromEntity(it)
            }
            }
    }

    override suspend fun insertNewsToDb(newsList: List<NewsEntity>) {
        newsDao.insertNews(newsList)
    }

    override fun setPeriodicWorkRequest() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val workRequest = PeriodicWorkRequest.Builder(
            PeriodicFetchWorker::class.java, 15, TimeUnit.MINUTES
        ).setConstraints(constraints).build()
        workManager.enqueueUniquePeriodicWork(
            "newsFetch",
            ExistingPeriodicWorkPolicy.UPDATE, workRequest
        )
    }

}