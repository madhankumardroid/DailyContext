package com.madhan.dailycontext

import android.app.Application
import di.startDI
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class ArticlesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startDI {
            androidLogger()
            androidContext(this@ArticlesApp)
        }
    }
}