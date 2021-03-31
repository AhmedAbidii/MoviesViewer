package com.aabidi.moviesviewer.movies.listing.domain

import com.aabidi.moviesviewer.core.networking.callApi
import com.aabidi.moviesviewer.data.movies.remote.MovieWebService

class MoviesListingRemote(
    private val movieWebService: MovieWebService
) : MoviesListingContract.Remote {
    override suspend fun getPopularMovies(requestPage: Int) =
        callApi { movieWebService.getPopularMovies(requestPage) }
}
