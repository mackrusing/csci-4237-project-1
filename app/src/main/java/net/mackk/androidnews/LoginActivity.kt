package net.mackk.androidnews

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class LoginActivity : ComponentActivity() {

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

    // state
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // check for valid user and password
    fun inputIsValid(): Boolean {
        return (username.length >= 5) && (password.length >= 8)
    }

    // handle auth
    fun handleSubmit() {
        val intent = Intent(context, ResultsActivity::class.java)
        intent.putExtra("username", username)
        intent.putExtra("password", password)
        context.startActivity(intent)
    }

    // sanitize input
    fun sanitize(input: String): String {
        return input.filterNot { it.isWhitespace() }
    }

    // ui
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        // username
        Column(
            modifier = Modifier.width(IntrinsicSize.Min)
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = sanitize(it) },
                label = { Text(text = "Username") },
            )
            Spacer(Modifier.height(1.dp))
            Text(
                text = "Username must be at least 5 characters",
                modifier = Modifier.alpha(if (username.isNotEmpty() && username.length < 5) 1f else 0f),
                color = Color(0xFFFF0000),
                style = MaterialTheme.typography.labelSmall
            )
        }

        // password
        Column(
            modifier = Modifier.width(IntrinsicSize.Min)
        ) {
            OutlinedTextField(
                value = password,
                onValueChange = { password = sanitize(it) },
                label = { Text(text = "Password") },
                visualTransformation = PasswordVisualTransformation(),
            )
            Spacer(Modifier.height(1.dp))
            Text(
                text = "Password must be at least 8 characters",
                modifier = Modifier.alpha(if (password.isNotEmpty() && password.length < 8) 1f else 0f),
                color = Color(0xFFFF0000),
                style = MaterialTheme.typography.labelSmall
            )
        }

        Spacer(Modifier.height(12.dp))

        // button
        Button(
            onClick = ::handleSubmit, enabled = inputIsValid()
        ) {
            Text(text = "Submit")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ActivityContentPreview() {
    ActivityBoilerplate { innerPadding -> ActivityContent(innerPadding) }
}
