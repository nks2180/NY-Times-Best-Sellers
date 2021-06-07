package com.nytimes.bestsellers

import android.app.Application
import com.nytimes.bestsellers.di.nyTimesModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

class NyTimes : Application()  {
    override fun onCreate() {
        super.onCreate()

            startKoin {
                androidContext(this@NyTimes)
                modules(nyTimesModule)
        }
    }
}
