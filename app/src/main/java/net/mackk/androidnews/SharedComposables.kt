package net.mackk.androidnews

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import net.mackk.androidnews.ui.theme.NewsTheme

@Composable
fun ActivityBoilerplate(content: @Composable ((PaddingValues) -> Unit)) {
    NewsTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            content(innerPadding)
        }
    }
}

@Composable
fun ArticleCard(articleData: ArticleData, modifier: Modifier = Modifier, dbg: Boolean = false) {
    Column(modifier = modifier.padding(12.dp)) {
        // image
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            if (dbg) {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
            } else {
                AsyncImage(
                    model = articleData.imageUrl,
                    contentDescription = null,
                )
            }
        }

        // article title
        Text(
            text = articleData.title,
            modifier = Modifier.padding(top = 12.dp),
            style = MaterialTheme.typography.headlineSmall
        )

        // article author
        Row(
            modifier = Modifier.padding(vertical = 6.dp)
        ) {
            Text(
                text = "${articleData.author} · ${articleData.sourceName}",
                style = MaterialTheme.typography.titleMedium
            )
        }

        // article body
        Text(text = articleData.description, style = MaterialTheme.typography.bodyMedium)

    }
}

@Preview(showBackground = true)
@Composable
fun ArticleCardPreview() {
    ArticleCard(
        articleData = sampleArticleData,
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        dbg = true
    )
}
