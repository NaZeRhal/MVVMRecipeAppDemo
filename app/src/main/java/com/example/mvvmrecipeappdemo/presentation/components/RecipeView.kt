package com.example.mvvmrecipeappdemo.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.mvvmrecipeappdemo.domain.model.Recipe
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun RecipeView(
    recipe: Recipe
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        item {
            recipe.featureImage?.let { url ->
                GlideImage(
                    imageModel = url,
                    contentDescription = "detailed recipe image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .requiredHeight(260.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                recipe.title?.let { title ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = title,
                            modifier = Modifier
                                .fillParentMaxWidth(0.85f)
                                .wrapContentWidth(align = Alignment.Start),
                            style = MaterialTheme.typography.h3
                        )
                        val rank = recipe.rating.toString()
                        Text(
                            text = rank,
                            modifier = Modifier
                                .fillParentMaxWidth(0.15f)
                                .wrapContentWidth(align = Alignment.End),
                            style = MaterialTheme.typography.h4
                        )
                    }
                    for (ingredient in recipe.ingredients) {
                        Text(
                            text = ingredient,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
        }

    }
}
