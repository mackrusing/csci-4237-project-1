package net.mackk.androidnews

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class SourcesActivity : ComponentActivity() {

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

    // state
    var dropdownExpanded by remember { mutableStateOf(false) }
    var dropdownCurrent by remember { mutableStateOf("all") }

    // values
    val sourcesList = listOf(
        sampleSourceData,
        sampleSourceData,
        sampleSourceData,
        sampleSourceData,
        sampleSourceData,
        sampleSourceData,
        sampleSourceData
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // search term
        Text(text = "Search: ${activity?.intent?.getStringExtra("search")}")

        // category
        Box {
            TextButton(onClick = {
                dropdownExpanded = !dropdownExpanded
            }) { Text(text = "Category: ${dropdownCurrent}") }
            DropdownMenu(
                expanded = dropdownExpanded,
                onDismissRequest = { dropdownExpanded = false },
            ) {
                DropdownMenuItem(
                    text = { Text(text = "all") },
                    onClick = { dropdownCurrent = "all" })
                DropdownMenuItem(
                    text = { Text(text = "business") },
                    onClick = { dropdownCurrent = "business" })
                DropdownMenuItem(
                    text = { Text(text = "entertainment") },
                    onClick = { dropdownCurrent = "entertainment" })
                DropdownMenuItem(
                    text = { Text(text = "general") },
                    onClick = { dropdownCurrent = "general" })
                DropdownMenuItem(
                    text = { Text(text = "health") },
                    onClick = { dropdownCurrent = "health" })
                DropdownMenuItem(
                    text = { Text(text = "science") },
                    onClick = { dropdownCurrent = "science" })
                DropdownMenuItem(
                    text = { Text(text = "sports") },
                    onClick = { dropdownCurrent = "sports" })
                DropdownMenuItem(
                    text = { Text(text = "technology") },
                    onClick = { dropdownCurrent = "technology" })
            }
        }

        // skip button
        Button(onClick = {}) { Text(text = "Skip") }

        // sources
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(sourcesList) { SourceCard(it) }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun ActivityContentPreview() {
    ActivityBoilerplate { innerPadding -> ActivityContent(innerPadding) }
}

private data class SourceData(
    val id: String,
    val name: String,
    val description: String,
    val url: String,
    val category: String,
    val language: String,
    val country: String,
)

private val sampleSourceData = SourceData(
    id = "abc-news",
    name = "ABC News",
    description = "Your trusted source for breaking news, analysis, exclusive interviews, headlines, and videos at ABCNews.com.",
    url = "https://abcnews.go.com",
    category = "general",
    language = "en",
    country = "us"
)

@Composable
private fun SourceCard(
    sourceData: SourceData,
    modifier: Modifier = Modifier,
) {
    val str = LocalContext.current
    Column(modifier = modifier.padding(12.dp)) {
        // source name
        Text(
            text = sourceData.name,
            modifier = Modifier.padding(top = 12.dp),
            style = MaterialTheme.typography.headlineSmall
        )

        // description
        Text(text = sourceData.description, style = MaterialTheme.typography.bodyMedium)
    }
}
