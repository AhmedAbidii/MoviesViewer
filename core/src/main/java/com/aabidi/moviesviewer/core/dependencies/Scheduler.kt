package com.aabidi.moviesviewer.core.dependencies

import com.aabidi.moviesviewer.core.dispatchers.AppDispatcher
import com.aabidi.moviesviewer.core.dispatchers.Dispatcher
import org.koin.dsl.module

val schedulerModule = module {

    /** The application-level dispatcher for scheduling work on threads **/
    single<Dispatcher> { AppDispatcher() }

}
