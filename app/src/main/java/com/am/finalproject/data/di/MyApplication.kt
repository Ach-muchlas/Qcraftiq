package com.am.finalproject.data.di

import android.app.Application
import com.am.finalproject.data.di.KoinModule.databaseModule
import com.am.finalproject.data.di.KoinModule.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    uiModule
                )
            )
        }
    }
}