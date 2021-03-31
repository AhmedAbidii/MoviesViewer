package com.aabidi.moviesviewer

import com.aabidi.moviesviewer.core.application.MainApplication
import com.aabidi.moviesviewer.data.util.daoModule
import com.aabidi.moviesviewer.data.util.webServiceModule
import com.aabidi.moviesviewer.data.MovieDatabase

class MainActivity : MainApplication() {

    override fun getDataModules() = arrayOf(
        daoModule(MovieDatabase.getDatabase(applicationContext)),
        webServiceModule
    )
}