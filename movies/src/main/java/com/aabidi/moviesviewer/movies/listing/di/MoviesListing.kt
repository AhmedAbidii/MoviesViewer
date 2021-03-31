package com.aabidi.moviesviewer.movies.listing.di

import com.aabidi.moviesviewer.core.dependencies.base.BaseModule
import com.aabidi.moviesviewer.movies.listing.domain.MoviesListingContract
import com.aabidi.moviesviewer.movies.listing.domain.MoviesListingRemote
import com.aabidi.moviesviewer.movies.listing.domain.MoviesListingRepo
import com.aabidi.moviesviewer.movies.listing.viewmodel.MoviesListingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module

/**
 * Dependency injection for Discover Movies
 */
object SectionMoviesModule : BaseModule {

    override fun load() {
        unloadKoinModules(sectionMoviesModule)
        loadKoinModules(sectionMoviesModule)
    }

    override fun unload() = unloadKoinModules(sectionMoviesModule)
}

private val sectionMoviesModule = module {

    factory<MoviesListingContract.Remote> { MoviesListingRemote(movieWebService = get()) }
    factory<MoviesListingContract.Repository> { MoviesListingRepo(remote = get()) }

    viewModel { MoviesListingViewModel(dispatcher = get(), sectionRepo = get()) }

}
