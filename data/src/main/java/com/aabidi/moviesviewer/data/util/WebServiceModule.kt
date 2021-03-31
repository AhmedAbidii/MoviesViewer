package com.aabidi.moviesviewer.data.util

import com.aabidi.moviesviewer.data.movies.remote.MovieWebService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

val webServiceModule = module {
    single { get<Retrofit>().create<MovieWebService>() }
}
