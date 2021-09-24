package com.example.mvvmrecipeappdemo.extensions

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun padding(dp: Int): Modifier = Modifier.padding(dp.dp)
fun paddingTop(dp: Int): Modifier = Modifier.padding(top = dp.dp)
fun paddingBottom(dp: Int): Modifier = Modifier.padding(bottom = dp.dp)
fun paddingStart(dp: Int): Modifier = Modifier.padding(start = dp.dp)
fun paddingEnd(dp: Int): Modifier = Modifier.padding(end = dp.dp)
fun paddingStartEnd(start: Int, end: Int) = Modifier.padding(start = start.dp, end = end.dp)
