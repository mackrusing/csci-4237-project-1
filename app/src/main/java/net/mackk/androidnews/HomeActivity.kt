package net.mackk.androidnews

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.edit

class HomeActivity : ComponentActivity() {

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

    // prefs
    val prefs = remember { context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE) }

    // state
    var search by remember { mutableStateOf(prefs.getString("username", "") ?: "") }

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // search box
        OutlinedTextField(
            value = search,
            onValueChange = {
                search = it
                prefs.edit { putString("search", search) }
            },
            label = { Text(text = "Search") },
        )

        // search button
        Button(
            onClick = {
                // intent
                val intent = Intent(context, SourcesActivity::class.java)
                intent.putExtra("search", search)
                context.startActivity(intent)
            }, enabled = search.isNotEmpty()
        ) {
            Text(text = "Submit")
        }

        // local news button
        Button(
            onClick = {},
        ) {
            Text(text = "Local News")
        }

        // local news button
        Button(
            onClick = {},
        ) {
            Text(text = "Top Headlines")
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun ActivityContentPreview() {
    ActivityBoilerplate { innerPadding -> ActivityContent(innerPadding) }
}
