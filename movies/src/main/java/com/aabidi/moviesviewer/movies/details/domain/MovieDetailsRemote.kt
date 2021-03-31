package com.aabidi.moviesviewer.movies.details.domain

import com.aabidi.moviesviewer.core.networking.callApi
import com.aabidi.moviesviewer.data.movies.remote.MovieWebService

class MovieDetailsRemote(private val movieWebService: MovieWebService) : MovieDetailsContract.Remote {

    override suspend fun getMovieDetails(movieId: String) = callApi { movieWebService.getMovieAsync(movieId = movieId) }

}
