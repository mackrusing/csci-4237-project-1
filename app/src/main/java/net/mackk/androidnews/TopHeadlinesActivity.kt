package net.mackk.androidnews

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TopHeadlinesActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ActivityBoilerplate { innerPadding -> ActivityContent(innerPadding) }
        }
    }
}

@Composable
private fun ActivityContent(innerPadding: PaddingValues = PaddingValues()) {

    // context
    val context = LocalContext.current
    val activity = when (context) {
        is Activity -> context
        else -> null
    }

    // stats
    var apiManager = remember { ApiManager() }
    var articlesList by remember { mutableStateOf<List<ArticleData>>(emptyList()) }

    // effects
    LaunchedEffect(true) {
        val result = withContext(Dispatchers.IO) {
            apiManager.getTopHeadlines(context.getString(R.string.news_api_key), 1)
        }
        articlesList = result
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // sources
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(articlesList) { ArticleCard(it) }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun ActivityContentPreview() {
    ActivityBoilerplate { innerPadding -> ActivityContent(innerPadding) }
}
