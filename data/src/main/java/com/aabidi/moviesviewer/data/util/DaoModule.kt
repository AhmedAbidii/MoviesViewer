package com.aabidi.moviesviewer.data.util

import com.aabidi.moviesviewer.data.MovieDatabase
import org.koin.dsl.module

fun daoModule(appDb: MovieDatabase) = module {
    single { appDb.movieDao() }
}
