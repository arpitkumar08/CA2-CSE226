package com.example.cse226

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Coroutine : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme{
                Surface(modifier = Modifier.fillMaxSize()) {
                    CouroutineBasiscsScreen()
                }
            }
        }
    }
}



@Composable
fun CouroutineBasiscsScreen(){
    var resultText by remember { mutableStateOf("Press button to start the background task") }

    var isLoading by remember {mutableStateOf(false)}

    var couroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = resultText, style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(24.dp))


        if(isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = {
                    isLoading = true
                    resultText = "Fetching data from background..."

                    couroutineScope.launch {
                        val data = fetchUserDataFromNetwork()

                        resultText = data
                        isLoading = false
                    }
                }
            ) {
                Text("Trigger background task...")
            }
        }
    }
}

suspend fun fetchUserDataFromNetwork():String {
    return withContext(Dispatchers.IO) {
        delay(3000)
        "Data Loaded, ID: ${System.currentTimeMillis() % 1000}"
    }
}

@Preview(showBackground = true)
@Composable
fun CoroutineScreenPreview() {
    CouroutineBasiscsScreen()
}