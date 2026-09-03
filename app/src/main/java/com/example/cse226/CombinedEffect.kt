package com.example.cse226

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.delay

class CombinedEffect : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CombinedEffectScreen()
        }
    }
}

@Composable
fun CombinedEffectScreen() {
    // Navigation state switching between Main and Details page
    var showMainPage by remember { mutableStateOf(true) }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            if (showMainPage) {
                MainPageScreen(
                    onNavigateToDetails = { showMainPage = false }
                )
            } else {
                DetailsPageScreen(
                    onNavigateBack = { showMainPage = true }
                )
            }
        }
    }
}

@Composable
fun MainPageScreen(onNavigateToDetails: () -> Unit) {
    var launchedEffectStatus by remember { mutableStateOf("Initializing...") }
    var disposableEffectStatus by remember { mutableStateOf("Listener Inactive") }

    // --- 1. LaunchedEffect ---
    // Runs an async task when MainPage enters composition.
    // Automatically CANCELLED when leaving MainPage.
    LaunchedEffect(Unit) {
        println("CSE226_LOG: [LaunchedEffect] Coroutine STARTED on MainPage")
        launchedEffectStatus = "LaunchedEffect: Task Running..."

        delay(4000) // Simulating a 4-second network request

        launchedEffectStatus = "LaunchedEffect: Task Completed! ✅"
        println("CSE226_LOG: [LaunchedEffect] Coroutine COMPLETED")
    }

    // --- 2. DisposableEffect ---
    // Sets up a listener when MainPage enters composition.
    // Immediately CLEARED via onDispose when leaving MainPage.
    DisposableEffect(Unit) {
        println("CSE226_LOG: [DisposableEffect] Listener SETUP executed on MainPage")
        disposableEffectStatus = "DisposableEffect: Listener ACTIVE 🟢"

        // Mandatory cleanup when MainPage leaves composition or unmounts
        onDispose {
            println("CSE226_LOG: [DisposableEffect] onDispose EXECUTED! Resource destroyed 🔴")
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(24.dp)
    ) {
        Text("📄 Main Page", fontSize = 22.sp)

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(launchedEffectStatus, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(disposableEffectStatus, fontSize = 14.sp)
            }
        }

        Button(onClick = onNavigateToDetails) {
            Text("Navigate to Details Page ➔")
        }
    }

}

@Composable
fun DetailsPageScreen(onNavigateBack: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("📄 Details Page", fontSize = 22.sp)
        Text("MainPage is now unmounted.", fontSize = 14.sp)

        Button(onClick = onNavigateBack) {
            Text("⬅️ Return to Main Page")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CombinedEffectScreenPreview() {
    // Apply your app theme if you have one, or call the composable directly
    CombinedEffectScreen()
}