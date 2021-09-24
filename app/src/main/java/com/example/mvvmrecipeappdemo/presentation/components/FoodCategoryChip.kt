package com.example.mvvmrecipeappdemo.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mvvmrecipeappdemo.extensions.mediumShape
import com.example.mvvmrecipeappdemo.extensions.padding
import com.example.mvvmrecipeappdemo.extensions.paddingEnd
import com.example.mvvmrecipeappdemo.extensions.primaryColor

@Composable
fun FoodCategoryChip(
    category: String,
    isSelected: Boolean = false,
    onSelectedCategoryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit
) {
    Surface(
        modifier = paddingEnd(8),
        elevation = 8.dp,
        shape = mediumShape(),
        color = primaryColor()
    ) {
        Row(
            modifier = Modifier
                .toggleable(
                    value = isSelected,
                    onValueChange = {
                        onSelectedCategoryChanged(category)
                        onExecuteSearch()
                    }
                )
        ) {
            Text(
                text = category,
                style = MaterialTheme.typography.body2,
                color = Color.White,
                modifier = padding(8)
            )
        }
    }
}