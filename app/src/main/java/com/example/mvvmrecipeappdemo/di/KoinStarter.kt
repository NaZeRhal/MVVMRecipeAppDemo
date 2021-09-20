package com.example.mvvmrecipeappdemo.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.Koin
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

object KoinStarter {
    @Synchronized
    fun start(context: Context): Koin {
        return GlobalContext.getOrNull() ?: startKoin {
            androidLogger(Level.NONE)
            androidContext(context.applicationContext)
            modules(listOf(dataDiModule))
        }.koin
    }
}