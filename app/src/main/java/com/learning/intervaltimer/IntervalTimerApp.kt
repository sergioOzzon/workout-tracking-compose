package com.learning.intervaltimer

import android.app.Application
import com.learning.intervaltimer.di.data.dataModule
import com.learning.intervaltimer.di.network.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class IntervalTimerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(applicationContext)

            modules(
                dataModule,
                networkModule
            )
        }
    }
}