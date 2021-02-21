package com.codeliner.clickerwithtimer

import android.app.Application
import timber.log.Timber

class ClickerWithTimerApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}