package net.mackk.androidnews

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
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

    // values from prev activity
    val search = activity?.intent?.getStringExtra("search") ?: ""

    // state
    var dropdownExpanded by remember { mutableStateOf(false) }
    var dropdownCurrent by remember { mutableStateOf("business") }
    var apiManager = remember { ApiManager() }
    var sourcesList by remember { mutableStateOf<List<SourceData>>(emptyList()) }

    // effects
    LaunchedEffect(dropdownCurrent) {
        val result = withContext(Dispatchers.IO) {
            apiManager.getSources(context.getString(R.string.news_api_key), dropdownCurrent)
        }
        sourcesList = result
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // search term
        Text(text = "Search: $search")

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
        Button(onClick = {
            // intent
            val intent = Intent(context, ResultsActivity::class.java)
            intent.putExtra("search", search)
            intent.putExtra("source_selected", false)
            context.startActivity(intent)
        }) { Text(text = "Skip") }

        // sources
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(sourcesList) { SourceCard(it, search) }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun ActivityContentPreview() {
    ActivityBoilerplate { innerPadding -> ActivityContent(innerPadding) }
}

@Composable
private fun SourceCard(
    sourceData: SourceData,
    search: String,
    modifier: Modifier = Modifier,
) {
    // context
    val context = LocalContext.current

    Column(
        modifier = modifier
            .padding(12.dp)
            .clickable(onClick = {
                // intent
                val intent = Intent(context, ResultsActivity::class.java)
                intent.putExtra("search", search)
                intent.putExtra("source_selected", true)
                intent.putExtra("source_id", sourceData.id)
                context.startActivity(intent)
            })) {
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
