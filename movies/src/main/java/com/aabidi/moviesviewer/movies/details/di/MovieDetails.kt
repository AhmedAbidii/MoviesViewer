package com.aabidi.moviesviewer.movies.details.di

import com.aabidi.moviesviewer.core.dependencies.base.BaseModule
import com.aabidi.moviesviewer.movies.details.domain.MovieDetailsContract
import com.aabidi.moviesviewer.movies.details.domain.MovieDetailsLocal
import com.aabidi.moviesviewer.movies.details.domain.MovieDetailsRemote
import com.aabidi.moviesviewer.movies.details.domain.MovieDetailsRepo
import com.aabidi.moviesviewer.movies.details.viewmodel.MovieDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module

/**
 * Dependency Injection module for Movie Details
 */
object MovieDetailsModule : BaseModule {
    override fun load() = loadKoinModules(movieDetailsModule)
    override fun unload() = unloadKoinModules(movieDetailsModule)
}

private val movieDetailsModule = module {

    factory<MovieDetailsContract.Local> { MovieDetailsLocal(movieDao = get()) }
    factory<MovieDetailsContract.Remote> { MovieDetailsRemote(movieWebService = get()) }
    factory<MovieDetailsContract.Repository> { MovieDetailsRepo(local = get(), remote = get()) }

    viewModel { MovieDetailsViewModel(dispatcher = get(), movieRepo = get()) }

}
