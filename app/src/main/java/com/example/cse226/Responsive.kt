package com.example.cse226

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cse226.ui.theme.CSE226Theme

class Responsive : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CSE226Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        ResponsiveScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun ResponsiveScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(16.dp)
        ) {
            if (maxWidth < 600.dp) {
                CompactLayout()
            } else {
                ExpandLayout()
            }
        }
    }
}

@Composable
private fun CompactLayout() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileAvatar(modifier = Modifier.height(120.dp).fillMaxWidth())

        Spacer(modifier = Modifier.height(16.dp))

        ProfileDetails()
    }
}

@Composable
private fun ExpandLayout() {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfileAvatar(modifier = Modifier.width(160.dp).height(120.dp))

        Spacer(modifier = Modifier.width(16.dp))

        Box(
            modifier = Modifier.weight(1f)
        ) {
            ProfileDetails()
        }
    }
}
@Composable
private fun ProfileAvatar(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF6200EE)),
        contentAlignment = Alignment.Center
    ) {
        Text("Avatar", color = Color.White, style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
private fun ProfileDetails() {
    Column {
        Text(
            text = "Arpit Kumar",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "Student, CSE",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
        )
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun ResponsiveScreenCompactPreview() {
    CSE226Theme {
        ResponsiveScreen()
    }
}

@Preview(showBackground = true, widthDp = 800)
@Composable
fun ResponsiveScreenExpandedPreview() {
    CSE226Theme {
        ResponsiveScreen()
    }
}
