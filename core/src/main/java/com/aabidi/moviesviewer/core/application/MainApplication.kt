package com.aabidi.moviesviewer.core.application

import android.app.Application
import com.aabidi.moviesviewer.core.BuildConfig
import com.aabidi.moviesviewer.core.dependencies.androidModule
import com.aabidi.moviesviewer.core.dependencies.networkModule
import com.aabidi.moviesviewer.core.dependencies.schedulerModule
import com.aabidi.moviesviewer.core.dependencies.storageModule
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module

/**
 * Android's Application class.
 * Used for 3rd party library init and other setup.
 */
abstract class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initialiseStetho()
        initialiseKoin()
    }

    private fun initialiseStetho() {
        if (BuildConfig.DEBUG) Stetho.initializeWithDefaults(this)
    }

    private fun initialiseKoin() {
        startKoin {

            // Logger for Android
            androidLogger()

            // Set the Android Context
            androidContext(this@MainApplication)

            modules(listOf(*defaultModules(), *getDataModules()))
        }
    }

    private fun defaultModules(): Array<Module> = arrayOf(androidModule, networkModule, storageModule, schedulerModule)

    /**
     * Return the modules for Data (Dao, WebServices)
     */
    abstract fun getDataModules(): Array<Module>

}
