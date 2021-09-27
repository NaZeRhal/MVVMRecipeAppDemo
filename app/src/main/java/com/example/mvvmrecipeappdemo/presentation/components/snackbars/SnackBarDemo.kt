package com.example.mvvmrecipeappdemo.presentation.components.snackbars

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun SnackBarDemo(
    isShowing: Boolean,
    onHideSnackBar: () -> Unit
) {
    if (isShowing) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val snack = createRef()
            Snackbar(
                modifier = Modifier.constrainAs(snack) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                action = {
                    Text(
                        text = "HIDE",
                        modifier = Modifier.clickable(onClick = onHideSnackBar),
                        style = MaterialTheme.typography.h5
                    )
                }
            ) {
                Text(text = "This is the snack bar")
            }
        }
    }
}