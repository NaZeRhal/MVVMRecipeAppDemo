package com.example.mvvmrecipeappdemo.presentation.ui

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import com.example.mvvmrecipeappdemo.di.dataDiModule
import com.example.mvvmrecipeappdemo.di.viewModelDiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {

    val isDarkTheme = mutableStateOf(false)

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MainApplication)
            modules(listOf(dataDiModule, viewModelDiModule))
        }
    }

    fun toggleTheme() {
        isDarkTheme.value = !isDarkTheme.value
    }
}