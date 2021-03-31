package com.aabidi.moviesviewer.core.dependencies

import androidx.preference.PreferenceManager
import org.koin.dsl.module

val storageModule = module {

    single { PreferenceManager.getDefaultSharedPreferences(get()) }

}
