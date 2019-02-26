package com.example.flickrsearch

import android.app.Application
import com.example.flickrsearch.di.appModule
    import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FlickrApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@FlickrApplication)
            logger()
            modules(appModule)
        }

    }

}