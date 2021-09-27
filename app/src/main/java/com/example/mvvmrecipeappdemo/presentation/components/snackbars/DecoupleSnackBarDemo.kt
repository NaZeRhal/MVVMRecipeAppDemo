package com.example.mvvmrecipeappdemo.presentation.components.snackbars

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun DecoupleSnackBarDemo(
    snackBarHostState: SnackbarHostState
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val snack = createRef()
        SnackbarHost(
            modifier = Modifier.constrainAs(snack) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            hostState = snackBarHostState,
            snackbar = {
                Snackbar(
                    action = {
                        TextButton(
                            onClick = { snackBarHostState.currentSnackbarData?.dismiss() }
                        ) {
                            Text(
                                text = snackBarHostState.currentSnackbarData?.actionLabel ?: "",
                                style = TextStyle(color = Color.White))
                        }
                    }
                ) {
                    Text(text = snackBarHostState.currentSnackbarData?.message ?: "")
                }
            }
        )
    }
}

