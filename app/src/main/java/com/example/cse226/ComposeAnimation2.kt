package com.example.cse226

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ComposeAnimation2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Animate()
        }
    }
}

enum class ActionState { IDLE, LOADING, SUCCESS }
@Composable
fun Animate() {
    var currentState by remember { mutableStateOf(ActionState.IDLE) }

    // 2. Parent Transition coordinating all child property animations
    val transition = updateTransition(
        targetState = currentState,
        label = "ButtonStateTransition"
    )

    // 3. Child properties synchronized to the parent transition state
    val buttonWidth by transition.animateDp(
        transitionSpec = { tween(durationMillis = 400) },
        label = "widthAnimation"
    ) { state ->
        when (state) {
            ActionState.IDLE -> 200.dp
            ActionState.LOADING -> 60.dp
            ActionState.SUCCESS -> 200.dp
        }
    }

    val backgroundColor by transition.animateColor(
        transitionSpec = { tween(durationMillis = 400) },
        label = "colorAnimation"
    ) { state ->
        when (state) {
            ActionState.IDLE -> Color(0xFF6200EE)
            ActionState.LOADING -> Color(0xFF757575)
            ActionState.SUCCESS -> Color(0xFF4CAF50)
        }
    }

    val cornerRadius by transition.animateDp(
        transitionSpec = { tween(durationMillis = 400) },
        label = "cornerAnimation"
    ) { state ->
        when (state) {
            ActionState.IDLE -> 12.dp
            ActionState.LOADING -> 30.dp
            ActionState.SUCCESS -> 12.dp
        }
    }

    // 4. UI Layout driven by animated values
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .width(buttonWidth)
                .height(60.dp)
                .clip(RoundedCornerShape(cornerRadius))
                .background(backgroundColor)
                .clickable {
                    currentState = when (currentState) {
                        ActionState.IDLE -> ActionState.LOADING
                        ActionState.LOADING -> ActionState.SUCCESS
                        ActionState.SUCCESS -> ActionState.IDLE
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            when (currentState) {
                ActionState.IDLE -> Text("Submit", color = Color.White)
                ActionState.LOADING -> CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = 2.dp,
                    modifier = Modifier.height(24.dp).width(24.dp)
                )
                ActionState.SUCCESS -> Text("Success!", color = Color.White)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AnimationPreview2() {
    Animate()
}
