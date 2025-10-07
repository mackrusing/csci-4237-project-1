package net.mackk.androidnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class ResultsActivity : ComponentActivity() {

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

    // values
    val articlesList = listOf(
        sampleArticleData,
        sampleArticleData,
        sampleArticleData,
        sampleArticleData,
        sampleArticleData,
        sampleArticleData,
        sampleArticleData
    )

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

@Preview(showBackground = true)
@Composable
private fun ActivityContentPreview() {
    ActivityBoilerplate { innerPadding -> ActivityContent(innerPadding) }
}
