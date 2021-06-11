package com.pavellukyanov.cinematic.core.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
//        this.applicationContext.deleteDatabase("MovieDatabase.db")
    }
}