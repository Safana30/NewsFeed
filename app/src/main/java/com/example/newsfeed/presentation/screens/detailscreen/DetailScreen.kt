package com.example.newsfeed.presentation.screens.detailscreen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.newsfeed.data.apiservice.apimodels.Article
import com.example.newsfeed.domain.models.ArticleBaseModel
import com.example.newsfeed.presentation.SharedViewModel
import com.example.newsfeed.presentation.components.BaseToolbar

@Composable
fun DetailScreen(
    sharedViewModel: SharedViewModel,
    onBack: () -> Unit = {}
) {
    val article = sharedViewModel.selectedArticle.collectAsState().value
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            BaseToolbar(
                title = "News Feed Detail",
                showBackButton = true, onBackPress = onBack
            )
        })
    { paddingValue ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
        ) {

            if (article != null) {
                DetailContent(article = article)
            }
        }

    }
}

@Composable
fun DetailContent(article: ArticleBaseModel) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        article.urlToImage?.let {
            AsyncImage(
                model = it,
                contentDescription = article.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = article.title ?: "No Title", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "By ${article.author ?: "Unknown"}",
            style = MaterialTheme.typography.labelSmall
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = article.description ?: "No Description")
        Spacer(modifier = Modifier.height(24.dp))


        article.url?.let {
            Button(modifier = Modifier.align(Alignment.End), onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                context.startActivity(intent)
            }) {
                Text("Read Full Article")
            }
        }
    }
}
