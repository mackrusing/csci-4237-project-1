package net.mackk.androidnews

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.mackk.androidnews.ui.theme.NewsTheme

@Composable
fun ActivityBoilerplate(content: @Composable ((PaddingValues) -> Unit)) {
    NewsTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            content(innerPadding)
        }
    }
}
