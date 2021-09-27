package com.example.mvvmrecipeappdemo.utils

import androidx.compose.material.ScaffoldState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SnackBarController(
    private val scope: CoroutineScope
) {
    private var snackBarJob: Job? = null

    init {
        cancelActiveJob()
    }

    fun getScope() = scope

    fun showSnackBar(scaffoldState: ScaffoldState, message: String, actionLabel: String) {
        if (snackBarJob != null) {
            cancelActiveJob()
        }
        snackBarJob = scope.launch {
            scaffoldState.snackbarHostState.showSnackbar(
                message = message,
                actionLabel = actionLabel
            )
            cancelActiveJob()
        }
    }

    private fun cancelActiveJob() {
        snackBarJob?.let { job ->
            job.cancel()
            snackBarJob = Job()
        }
    }
}