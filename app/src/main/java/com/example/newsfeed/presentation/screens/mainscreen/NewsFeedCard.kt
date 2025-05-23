package com.example.newsfeed.presentation.screens.mainscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.newsfeed.data.apiservice.apimodels.Article
import com.example.newsfeed.domain.models.ArticleBaseModel

@Composable
fun NewsFeedCard(articles: ArticleBaseModel?, onClick: () -> Unit){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable(onClick = onClick)) {
        Column(modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)) {
            articles?.urlToImage?.let {
                AsyncImage(model = it, contentDescription = null)
            }
            Text(articles?.title ?: "", fontWeight = FontWeight.Bold)
            Text(articles?.description ?: "", maxLines = 2, overflow = TextOverflow.Ellipsis)
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
                Text(text = "Published At : ", style = MaterialTheme.typography.labelSmall)
                Text(articles?.publishedAt ?: "", style = MaterialTheme.typography.labelSmall)
            }

        }
    }
}