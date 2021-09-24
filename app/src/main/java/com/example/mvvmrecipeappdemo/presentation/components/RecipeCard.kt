package com.example.mvvmrecipeappdemo.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mvvmrecipeappdemo.domain.model.Recipe
import com.example.mvvmrecipeappdemo.extensions.smallShape
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun RecipeCard(
    recipe: Recipe,
    onClick: () -> Unit
) {
    Card(
        shape = smallShape(),
        modifier = Modifier
            .padding(
                8.dp
            )
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 8.dp
    ) {
        Column() {
            recipe.featureImage?.let { url ->
                GlideImage(
                    imageModel = url,
                    contentDescription = "placeholder",
                    modifier = Modifier
                        .fillMaxWidth()
                        .requiredHeight(225.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 12.dp, end = 8.dp, start = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                recipe.title?.let { title ->
                    Text(
                        text = title,
                        modifier = Modifier
                            .fillMaxWidth(0.75f)
                            .wrapContentWidth(align = Alignment.Start)
                            .alignByBaseline(),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }
                recipe.rating?.let { rating ->
                    Text(
                        text = rating.toString(),
                        modifier = Modifier
                            .fillMaxWidth(0.25f)
                            .wrapContentWidth(align = Alignment.End)
                            .alignByBaseline(),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}