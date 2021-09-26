package com.example.mvvmrecipeappdemo.presentation.animation

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mvvmrecipeappdemo.R


private enum class ImageState {
    SMALL, BIG
}

@Composable
fun ExpandingImage() {
    var imageState by remember { mutableStateOf(ImageState.SMALL) }
    val transition = updateTransition(targetState = imageState, label = "ImageState")

    val size by transition.animateDp(
        label = "SizeAnimation",
        transitionSpec = {
            if (targetState == ImageState.SMALL) {
                spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessVeryLow
                )
            } else {
                spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            }
        }
    ) { state ->
        when (state) {
            ImageState.SMALL -> 70.dp
            ImageState.BIG -> 200.dp
        }
    }

    val color by transition.animateColor(
        label = "ColorAnimation"
    ) { state ->
        when (state) {
            ImageState.SMALL -> Color.LightGray
            ImageState.BIG -> Color.Yellow
        }
    }

    Image(
        painter = painterResource(id = R.drawable.stars_png),
        contentDescription = "pulsing star",
        modifier = Modifier
            .size(size)
            .clickable {
                imageState = when (imageState) {
                    ImageState.SMALL -> ImageState.BIG
                    ImageState.BIG -> ImageState.SMALL
                }
            },
        colorFilter = ColorFilter.tint(color = color)
    )
}
