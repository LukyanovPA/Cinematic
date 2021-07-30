package com.pavellukyanov.cinematic.core.app

import android.app.Application
import com.pavellukyanov.cinematic.utils.APP_METRICA_KEY
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        //удалить все таблицы из базы
//        this.applicationContext.deleteDatabase("MovieDatabase.db")
        initMetrica()
    }

    private fun initMetrica() {
        val config = YandexMetricaConfig.newConfigBuilder(APP_METRICA_KEY).build()
        YandexMetrica.activate(applicationContext, config)
        YandexMetrica.enableActivityAutoTracking(this)
    }
}