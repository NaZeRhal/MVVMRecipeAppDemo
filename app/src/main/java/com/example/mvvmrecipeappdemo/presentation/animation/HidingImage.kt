package com.example.mvvmrecipeappdemo.presentation.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@ExperimentalAnimationApi
@Composable
fun HidingImage() {
    var visible by remember { mutableStateOf(true) }

    Column {
        Button(onClick = { visible = !visible }) {
            Text(text = if (visible) "HIDE" else "SHOW")
        }
        AnimatedVisibility(visible = visible) {
            Box(
                modifier = Modifier
                    .requiredSize(128.dp)
                    .background(Color.Blue)
            )
        }
    }
}