package com.example.mvvmrecipeappdemo.presentation.animation

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private enum class PictureState(private val color: Color, private val size: Dp) {
    INITIAL(
        color = Color.LightGray,
        size = 40.dp
    ),
    FINISH(
        color = Color.Yellow,
        size = 50.dp
    );

    fun getColor() = this.color
    fun getSize() = this.size
}

@ExperimentalFoundationApi
@Composable
fun PulsingCircle() {
    val infiniteTransition = rememberInfiniteTransition()
    val size by infiniteTransition.animateFloat(
        initialValue = PictureState.INITIAL.getSize().value,
        targetValue = PictureState.FINISH.getSize().value,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 400, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val color by infiniteTransition.animateColor(
        initialValue = PictureState.INITIAL.getColor(),
        targetValue = PictureState.FINISH.getColor(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 400, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .height(55.dp),
        contentDescription = "PulsingCircle"
    ) {
        drawCircle(
            radius = size,
            brush = SolidColor(color)
        )
    }

}