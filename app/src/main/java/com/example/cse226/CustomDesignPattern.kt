package com.example.cse226

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CustomDesignPattern : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SelectableCardContainer()
        }
    }
}

    @Composable
    fun SelectableCardSlot(
        isSelected: Boolean,
        onSelect: () -> Unit,
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .clickable { onSelect() }
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = isSelected,
                    onClick = onSelect
                )
                Spacer(modifier = Modifier.width(12.dp))
                // The Slot: Caller inserts custom UI here
                Box(modifier = Modifier.weight(1f)) {
                    content()
                }
            }
        }
    }


    // 2. STATEFUL CONTAINER (State Hoisting)
    @Composable
    fun SelectableCardContainer() {
        var selectedOption by remember { mutableStateOf(1) }

        Column(modifier = Modifier.padding(16.dp)) {
            SelectableCardSlot(
                isSelected = (selectedOption == 1),
                onSelect = { selectedOption = 1 }
            ) {
                Text(text = "Option 1: Basic Text Payload")
            }

            Spacer(modifier = Modifier.padding(top = 8.dp))

            SelectableCardSlot(
                isSelected = (selectedOption == 2),
                onSelect = { selectedOption = 2 }
            ) {
                Column {
                    Text(text = "Option 2: Multi-line Slot")
                    Text(text = "Custom sub-heading layout")
                }
            }
        }
    }
    // 3. PREVIEW FUNCTION


@Preview(showBackground = true)
@Composable
fun SelectableCardSlotPreview() {
    SelectableCardSlot(
        isSelected = true,
        onSelect = {},
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Previewing Stateless Slot Content")
    }
}